package jt.projects.gbmovecar.ui.home

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnticipateOvershootInterpolator
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.transition.ArcMotion
import androidx.transition.ChangeBounds
import androidx.transition.TransitionManager
import androidx.transition.TransitionSet
import jt.projects.gbmovecar.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    companion object {
        const val ANIMATION_DURATION = 1000L
        const val INC_SIZE = 100
    }

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private var toRightAnimation = true


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initCar()
    }

    private fun initCar() {
        toRightAnimation = true

        binding.ivCar.setOnClickListener { car ->

            val transitionSet = TransitionSet()
                .addTransition(
                    ChangeBounds()
                        .apply {
                            duration = 1000L
                            setPathMotion(ArcMotion())
                            interpolator = AnticipateOvershootInterpolator(1.0f)
                        }
                )

            val params = car.layoutParams as FrameLayout.LayoutParams

            if (toRightAnimation) {
                params.gravity = Gravity.END or Gravity.BOTTOM
                params.height += INC_SIZE
                params.width += INC_SIZE
            } else {
                params.gravity = Gravity.START or Gravity.TOP
                params.height -= INC_SIZE
                params.width -= INC_SIZE
            }

            car.layoutParams = params
            TransitionManager.beginDelayedTransition(binding.containerTransitions, transitionSet)

            car.animate().setDuration(ANIMATION_DURATION).rotationBy(-180f)

            toRightAnimation = !toRightAnimation
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}