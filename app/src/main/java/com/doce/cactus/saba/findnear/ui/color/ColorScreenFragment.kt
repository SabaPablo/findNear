package com.doce.cactus.saba.findnear.ui.color

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.doce.cactus.saba.findnear.MainActivity
import com.doce.cactus.saba.findnear.R
import com.doce.cactus.saba.findnear.databinding.FragmentColorScreenBinding
import com.flask.colorpicker.ColorPickerView
import com.flask.colorpicker.builder.ColorPickerDialogBuilder
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ColorScreenFragment : Fragment(R.layout.fragment_color_screen) {

    private var _binding: FragmentColorScreenBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        _binding = FragmentColorScreenBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as MainActivity).showSystemUI()
        binding.backCl.setOnClickListener {
            chageColor()
        }




        viewLifecycleOwner.lifecycleScope.launch {
            delay(4000)
            val animacion = AlphaAnimation(1.0f, 0.0f)
            animacion.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation) {}
                override fun onAnimationRepeat(animation: Animation) {}
                override fun onAnimationEnd(animation: Animation) {
                    binding.leyendTv.visibility= View.GONE
                }
            })

            animacion.duration = 4000 // duración de la animación en milisegundos
            binding.leyendTv.startAnimation(animacion)
        }

    }


    private fun chageColor(){

        ColorPickerDialogBuilder
            .with(requireContext())
            .setTitle("Choose color")
            .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
            .density(12)
            .setOnColorSelectedListener { selectedColor ->
                changeBackgroundColor(selectedColor)
            }
            .setPositiveButton("ok"
            ) { _, lastSelectedColor, _ -> changeBackgroundColor(lastSelectedColor) }

            .build().show()


    }

    private fun changeBackgroundColor(lastSelectedColor: Int) {
        binding.backCl.setBackgroundColor(lastSelectedColor);

    }

}