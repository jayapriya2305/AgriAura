package com.priya.agriaura.network

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

    // ---------- AUTH ----------

    @POST("register.php")
    suspend fun register(
        @Body request: RegisterRequest
    ): Response<RegisterResponse>

    @POST("login.php")
    suspend fun login(
        @Body request: LoginRequest
    ): Response<LoginResponse>

    @POST("forgot_password.php")
    suspend fun forgotPassword(
        @Body request: ForgotPasswordRequest
    ): Response<ForgotPasswordResponse>

    // ---------- GARDEN SETUP ----------

    @POST("save_garden_space.php")
    suspend fun saveGardenSpace(
        @Body request: GardenSpaceRequest
    ): Response<GardenSpaceResponse>

    @POST("save_plan_space.php")
    suspend fun savePlanSpace(
        @Body request: PlanSpaceRequest
    ): Response<PlanSpaceResponse>

    @POST("save_containers.php")
    suspend fun saveContainers(
        @Body request: ContainerRequest
    ): Response<ContainerResponse>

    @POST("save_grow_preferences.php")
    suspend fun saveGrowPreferences(
        @Body request: GrowPreferenceRequest
    ): Response<GrowPreferenceResponse>

    // ---------- PLANTS ----------

    @POST("plant_recommendations.php")
    suspend fun getPlantRecommendations(
        @Body request: PlantRecommendationRequest
    ): Response<PlantRecommendationResponse>

    @GET("get_recommended_crops.php")
    suspend fun getRecommendedCrops(
        @Query("ph") ph: Float,
        @Query("texture") texture: String,
        @Query("district") district: String
    ): Response<RecommendedCropsResponse>

    @GET("get_crop_details.php")
    suspend fun getCropDetails(
        @Query("crop_name") cropName: String
    ): Response<CropDetailResponse>

    @POST("add_plant.php")
    suspend fun addPlant(
        @Body request: AddPlantRequest
    ): Response<AddPlantResponse>
}
