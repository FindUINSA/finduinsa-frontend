package com.finduinsa.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.finduinsa.data.repository.PostRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update

@OptIn(FlowPreview::class)
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val postRepository: PostRepository
) : ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> = _state.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    init {
        // Obeservasi posts dari repository. Setiap kali repository berubah, ini akan update.
        postRepository.getPosts()
            .onStart { _state.update { it.copy(isLoading = true, error = null) } }
            .onEach { allPosts -> // allPosts adalah daftar lengkap dari repository
                val filteredPosts = if (_searchQuery.value.isBlank()) {
                    allPosts // Jika tidak ada query, tampilkan semua
                } else {
                    allPosts.filter {
                        it.title.contains(_searchQuery.value, ignoreCase = true) ||
                                it.description.contains(_searchQuery.value, ignoreCase = true) ||
                                it.user.contains(_searchQuery.value, ignoreCase = true) ||
                                it.category.contains(_searchQuery.value, ignoreCase = true)
                    }
                }
                _state.update { currentState ->
                    currentState.copy(posts = filteredPosts, isLoading = false)
                }
            }
            .catch { e ->
                _state.update { it.copy(isLoading = false, error = e.localizedMessage ?: "Terjadi kesalahan") }
            }
            .launchIn(viewModelScope)

        // Debounce search query untuk memicu pemfilteran
        _searchQuery
            .debounce(300L)
            .onEach { query ->
                // Ketika query berubah, cukup trigger ulang observasi posts
                // Ini akan memicu onEach di atas dan memfilter ulang
                // Atau, bisa juga dengan memanggil loadAllPosts() jika Anda ingin memuat ulang dari sumber eksternal
                // Untuk contoh ini, cukup mengandalkan Flow dari repository yang sudah di-subscribe.
            }
            .launchIn(viewModelScope)
    }

    // Fungsi loadAllPosts() sekarang bisa lebih sederhana, atau bahkan dihilangkan
    // karena observasi Flow sudah dilakukan di init
    /*
    private fun loadAllPosts() {
        // Logika ini sudah ada di init{} block, jadi bisa dihapus duplikasinya
    }
    */

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
    }
}