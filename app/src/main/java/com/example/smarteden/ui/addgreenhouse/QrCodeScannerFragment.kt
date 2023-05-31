package com.example.smarteden.ui.addgreenhouse

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.smarteden.R
import com.example.smarteden.data.FireStoreViewModel
import com.google.zxing.Result
import me.dm7.barcodescanner.zxing.ZXingScannerView


class QrCodeScannerFragment : Fragment(), ZXingScannerView.ResultHandler {
    private var scannerView: ZXingScannerView? = null
    private val fireStoreViewModel: FireStoreViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        scannerView = ZXingScannerView(requireContext())
        return scannerView
    }

    override fun onResume() {
        super.onResume()
        scannerView?.setResultHandler(this)
        scannerView?.startCamera()
    }

    override fun onPause() {
        super.onPause()
        scannerView?.stopCamera()
    }

    private fun requestCameraPermission() {
        if (ContextCompat.checkSelfPermission(requireContext(),
                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                requireActivity(), arrayOf(Manifest.permission.CAMERA),
                REQUEST_CAMERA_PERMISSION)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                scannerView?.startCamera()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        requestCameraPermission()
    }

    override fun onStop() {
        super.onStop()
        scannerView?.stopCamera()
    }

    override fun handleResult(result: Result?) {
        Log.d(TAG, "handleResult: ${result?.text}")
        Toast.makeText(context, "${result?.text}", Toast.LENGTH_SHORT).show()
        if(fireStoreViewModel.connectGreenhouseQR(result?.text!!))
            findNavController().navigate(R.id.action_qrCodeScannerFragment_to_HomeFragment)
        scannerView?.resumeCameraPreview(this)
    }

    companion object {
        private const val TAG = "QRCodeScanner"
        private const val REQUEST_CAMERA_PERMISSION = 1
    }
}
