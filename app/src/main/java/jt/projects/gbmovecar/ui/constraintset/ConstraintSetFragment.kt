package jt.projects.gbmovecar.ui.constraintset

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnticipateOvershootInterpolator
import androidx.constraintlayout.widget.ConstraintSet
import androidx.fragment.app.Fragment
import androidx.transition.ArcMotion
import androidx.transition.ChangeBounds
import androidx.transition.TransitionManager
import androidx.transition.TransitionSet
import jt.projects.gbmovecar.R
import jt.projects.gbmovecar.databinding.FragmentCarStartBinding

//Внимание!
//Приложение упадёт, если в нашем контейнере constraint_container будут view без id. Id нужен даже
//там, где вы не используете какой-то view напрямую. То же самое касается view, которые вы создаете
//динамически. Им тоже нужно присвоить id программно, чтобы анимации запустились.
class ConstraintSetFragment : Fragment() {
    companion object {
        const val ANIMATION_DURATION = 1000L
    }

    private val transitionSet = TransitionSet()
        .addTransition(
            ChangeBounds()
                .apply {
                    duration = ANIMATION_DURATION
                    setPathMotion(ArcMotion())
                    interpolator = AnticipateOvershootInterpolator(1.0f)
                }
        )

    private var _binding: FragmentCarStartBinding? = null
    private val binding get() = _binding!!

    var toDownAnimation = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCarStartBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toDownAnimation = true

        binding.ivCar.setOnClickListener {
            if (toDownAnimation) moveCarToDown()
            else moveCarToUp()
        }
    }


    private fun moveCarToDown() {
        val constraintSet = ConstraintSet()
        constraintSet.clone(requireContext(), R.layout.fragment_car_end)
        TransitionManager.beginDelayedTransition(binding.constraintContainer, transitionSet)
        constraintSet.applyTo(binding.constraintContainer)
        toDownAnimation = false
    }

    private fun moveCarToUp() {
        val constraintSet = ConstraintSet()
        constraintSet.clone(requireContext(), R.layout.fragment_car_start)
        TransitionManager.beginDelayedTransition(binding.constraintContainer, transitionSet)
        constraintSet.applyTo(binding.constraintContainer)
        toDownAnimation = true
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}