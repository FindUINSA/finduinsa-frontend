package com.finduinsa.presentation.FoundReport


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class FoundReportViewModel @Inject constructor() : ViewModel() {

    private val _itemName = MutableStateFlow("")
    val itemName: StateFlow<String> = _itemName.asStateFlow()

    private val _itemDescription = MutableStateFlow("")
    val itemDescription: StateFlow<String> = _itemDescription.asStateFlow()

    private val _selectedCategory = MutableStateFlow<String?>(null)
    val selectedCategory: StateFlow<String?> = _selectedCategory.asStateFlow()

    private val _isCategoryDropdownExpanded = MutableStateFlow(false)
    val isCategoryDropdownExpanded: StateFlow<Boolean> = _isCategoryDropdownExpanded.asStateFlow()

    private val _foundDate = MutableStateFlow<Long?>(null) // Timestamp in milliseconds
    val foundDate: StateFlow<Long?> = _foundDate.asStateFlow()

    private val _foundLocation = MutableStateFlow("")
    val foundLocation: StateFlow<String> = _foundLocation.asStateFlow()

    private val _imageUrl = MutableStateFlow<String?>(null)
    val imageUrl: StateFlow<String?> = _imageUrl.asStateFlow()

    val categories = listOf("Elektronik", "Non-Elektronik", "Dokumen", "Aksesoris", "Lainnya")

    fun onNameChange(name: String) {
        _itemName.value = name
    }

    fun onDescriptionChange(description: String) {
        _itemDescription.value = description
    }

    fun onCategorySelected(category: String) {
        _selectedCategory.value = category
        _isCategoryDropdownExpanded.value = false
    }

    fun onToggleCategoryDropdown() {
        _isCategoryDropdownExpanded.update { !it }
    }

    fun onFoundDateSelected(date: Long?) {
        _foundDate.value = date
    }

    fun onFoundLocationChange(location: String) {
        _foundLocation.value = location
    }

    fun onImagePicked(uri: String?) {
        _imageUrl.value = uri
    }

    fun submitFoundReport() {
        // TODO: Implement submission logic (e.g., send data to repository/API)
        println("Found Report Submitted:")
        println("Nama Barang: ${itemName.value}")
        println("Deskripsi: ${itemDescription.value}")
        println("Kategori: ${selectedCategory.value}")
        println("Tanggal Penemuan: ${foundDate.value?.let { java.text.SimpleDateFormat("dd/MM/yyyy").format(java.util.Date(it)) } ?: "Belum dipilih"}")
        println("Lokasi Penemuan: ${foundLocation.value}")
        println("Image URL: ${imageUrl.value}")

        // Clear form after submission (optional)
        _itemName.value = ""
        _itemDescription.value = ""
        _selectedCategory.value = null
        _foundDate.value = null
        _foundLocation.value = ""
        _imageUrl.value = null
    }
}