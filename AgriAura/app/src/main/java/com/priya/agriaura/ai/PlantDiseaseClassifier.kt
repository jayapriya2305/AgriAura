package com.priya.agriaura.ai

import android.content.Context
import android.graphics.Bitmap
import com.priya.agriaura.data.DiseaseInfo
import com.priya.agriaura.data.diseaseInfoMap
import org.tensorflow.lite.Interpreter
import org.tensorflow.lite.support.common.FileUtil
import org.tensorflow.lite.support.image.TensorImage

class PlantDiseaseClassifier(context: Context) {

    private val interpreter: Interpreter
    private val labels: List<String>

    init {
        val model = FileUtil.loadMappedFile(context, "plant_disease_model.tflite")
        interpreter = Interpreter(model)
        labels = FileUtil.loadLabels(context, "labels.json")
    }

    fun predict(bitmap: Bitmap): Triple<String, Float, DiseaseInfo?> {
        val resized = Bitmap.createScaledBitmap(bitmap, 224, 224, true)
        val tensorImage = TensorImage.fromBitmap(resized)

        val output = Array(1) { FloatArray(labels.size) }
        interpreter.run(tensorImage.buffer, output)

        val scores = output[0]
        val maxIndex = scores.indices.maxBy { scores[it] }
        val confidence = scores[maxIndex]

        val label = labels[maxIndex]
        val info = diseaseInfoMap[label]

        return Triple(label, confidence, info)
    }
}
