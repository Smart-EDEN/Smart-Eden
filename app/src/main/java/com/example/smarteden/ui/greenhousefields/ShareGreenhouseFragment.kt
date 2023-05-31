package com.example.smarteden.ui.greenhousefields

import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.smarteden.data.FieldViewModel
import com.example.smarteden.databinding.FragmentShareGreenhouseBinding
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.qrcode.QRCodeWriter
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel
import java.util.*


class ShareGreenhouseFragment : Fragment() {

    private val fieldViewModel: FieldViewModel by activityViewModels()

    private var _binding: FragmentShareGreenhouseBinding? = null

    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentShareGreenhouseBinding.inflate(inflater, container, false)

        //val serialNumber = fieldViewModel.serialNumber
        val text = fieldViewModel.serialNumber
        val width = QR_WIDTH
        val height = QR_HEIGHT
        val hints = EnumMap<EncodeHintType, Any>(EncodeHintType::class.java)
        hints[EncodeHintType.ERROR_CORRECTION] = ErrorCorrectionLevel.H
        hints[EncodeHintType.CHARACTER_SET] = "UTF-8"
        val bitMatrix = QRCodeWriter().encode(text, BarcodeFormat.QR_CODE, width, height, hints)

        val bitmap = Bitmap.createBitmap(bitMatrix.width, bitMatrix.height, Bitmap.Config.RGB_565)
        for (x in 0 until bitMatrix.width) {
            for (y in 0 until bitMatrix.height) {
                bitmap.setPixel(x, y, if (bitMatrix[x, y]) Color.BLACK else Color.WHITE)
            }
        }
        val imageView = binding.qrCodeGreenhouse
        imageView.setImageBitmap(bitmap)


        return binding.root
    }

    companion object {
        const val QR_HEIGHT = 500
        const val QR_WIDTH = 500
    }
}
