package com.example.smarteden.ui.livepic

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.example.smarteden.data.LivePictureViewModel
import com.example.smarteden.databinding.FragmentLivePictureBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay
import org.junit.Ignore


@Ignore("UI")
class LivePictureFragment : Fragment() {

    private val livePictureViewModel: LivePictureViewModel by activityViewModels()

    private val scope = CoroutineScope(Dispatchers.Main)
    private var _binding: FragmentLivePictureBinding? = null

    private lateinit var imageView: ImageView

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLivePictureBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        imageView = binding.imgLive
        val progressBar = binding.progressBar

        livePictureViewModel.today.observe(viewLifecycleOwner) { urlToday ->
            progressBar.visibility = View.GONE
            if (urlToday == "") {
                Toast.makeText(context, "Heute kein Bild verfügbar", Toast.LENGTH_LONG).show()
            } else {
                Glide.with(this@LivePictureFragment)
                    .load(urlToday)
                    .into(imageView)
            }
        }

        livePictureViewModel.allPicturesLoaded.observe(viewLifecycleOwner) { allPicturesLoaded ->
            if(allPicturesLoaded) {
                binding.play.visibility = View.VISIBLE
            }
        }
        binding.play.setOnClickListener {
            makeVideo()
        }
    }
    private fun makeVideo() {
        scope.launch {
            for(item in livePictureViewModel.imageUrls.value!!) {
                Log.d("Bier", item)
                Glide.with(this@LivePictureFragment)
                    .load(item)
                    .into(imageView)
                delay(DELAY)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        livePictureViewModel.getToday()
        livePictureViewModel.getLastImages()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        scope.cancel()
        _binding = null
    }

    companion object{
        private const val DELAY = 1000L
    }
}
