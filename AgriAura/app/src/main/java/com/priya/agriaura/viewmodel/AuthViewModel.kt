package com.priya.agriaura.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.priya.agriaura.model.Plant
import com.priya.agriaura.network.*
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {

    // ================= AUTH =================

    fun register(
        request: RegisterRequest,
        onResult: (String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.api.register(request)
                onResult(response.body()?.status ?: "error")
            } catch (e: Exception) {
                onResult("network_error")
            }
        }
    }

    fun login(
        request: LoginRequest,
        onResult: (LoginResponse?) -> Unit
    ) {
        viewModelScope.launch {
            try {
                onResult(RetrofitClient.api.login(request).body())
            } catch (e: Exception) {
                onResult(null)
            }
        }
    }

    fun forgotPassword(
        request: ForgotPasswordRequest,
        onResult: (String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.api.forgotPassword(request)
                onResult(response.body()?.status ?: "error")
            } catch (e: Exception) {
                onResult("network_error")
            }
        }
    }

    // ================= GARDEN SETUP =================

    fun saveGardenSpace(
        request: GardenSpaceRequest,
        onResult: (String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                onResult(RetrofitClient.api.saveGardenSpace(request).body()?.status ?: "error")
            } catch (e: Exception) {
                onResult("network_error")
            }
        }
    }

    fun savePlanSpace(
        request: PlanSpaceRequest,
        onResult: (String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                onResult(RetrofitClient.api.savePlanSpace(request).body()?.status ?: "error")
            } catch (e: Exception) {
                onResult("network_error")
            }
        }
    }

    fun saveContainers(
        request: ContainerRequest,
        onResult: (String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                onResult(RetrofitClient.api.saveContainers(request).body()?.status ?: "error")
            } catch (e: Exception) {
                onResult("network_error")
            }
        }
    }

    fun saveGrowPreferences(
        request: GrowPreferenceRequest,
        onResult: (String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                onResult(RetrofitClient.api.saveGrowPreferences(request).body()?.status ?: "error")
            } catch (e: Exception) {
                onResult("network_error")
            }
        }
    }

    // ================= PLANT RECOMMENDATIONS =================

    fun getPlantRecommendations(
        request: PlantRecommendationRequest,
        onResult: (List<Plant>) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.api.getPlantRecommendations(request)
                onResult(response.body()?.plants ?: emptyList())
            } catch (e: Exception) {
                onResult(emptyList())
            }
        }
    }

    fun getRecommendedCrops(
        ph: Float,
        texture: String,
        district: String,
        onResult: (RecommendedCropsResponse?) -> Unit
    ) {
        viewModelScope.launch {
            try {
                onResult(
                    RetrofitClient.api
                        .getRecommendedCrops(ph, texture, district)
                        .body()
                )
            } catch (e: Exception) {
                onResult(null)
            }
        }
    }

    fun getCropDetails(
        cropName: String,
        onResult: (CropDetailResponse?) -> Unit
    ) {
        viewModelScope.launch {
            try {
                onResult(
                    RetrofitClient.api
                        .getCropDetails(cropName)
                        .body()
                )
            } catch (e: Exception) {
                onResult(null)
            }
        }
    }

    // ================= MY GARDEN =================

    // ================= MY GARDEN =================
    fun getMyGarden(
        onResult: (List<Plant>) -> Unit
    ) {
        viewModelScope.launch {
            // Backend not wired yet → return empty list safely
            onResult(emptyList())
        }
    }




    fun addPlant(
        request: AddPlantRequest,
        onResult: (String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.api.addPlant(request)
                if (response.isSuccessful) {
                    onResult("success")
                } else {
                    onResult("error")
                }
            } catch (e: Exception) {
                onResult("network_error")
            }
        }
    }

}
