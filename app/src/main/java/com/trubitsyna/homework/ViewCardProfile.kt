package com.trubitsyna.homework

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.core.content.withStyledAttributes
import com.google.android.material.card.MaterialCardView
import com.trubitsyna.homework.databinding.ViewCardProfileBinding

class ViewCardProfile @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : MaterialCardView(context, attrs, defStyleAttr) {
    private val binding: ViewCardProfileBinding by lazy {
        ViewCardProfileBinding.inflate(
            LayoutInflater.from(context),
            this
        )
    }

    init {
        context.withStyledAttributes(
            attrs,
            R.styleable.ViewCardProfile
        ) {
            getString(R.styleable.ViewCardProfile_title)?.let { name ->
                binding.textViewName.text = name
            }
            getString(R.styleable.ViewCardProfile_subtitle)?.let { subtitle ->
                binding.textViewProfileSubtext.text = subtitle
            }
            getDrawable(R.styleable.ViewCardProfile_profileImage)?.let { drawable ->
                binding.imageViewProfile.setImageDrawable(drawable)
            }
            getInt(R.styleable.ViewCardProfile_imageCounter, 0).let { num ->
                binding.textViewImageCounter.text = num.toString()
            }
            getInt(R.styleable.ViewCardProfile_subscriberCounter, 0).let { num ->
                binding.textViewSubscribeCounter.text = num.toString()
            }
            getInt(R.styleable.ViewCardProfile_postCounter, 0).let { num ->
                binding.textViewPostCounter.text = num.toString()
            }
            getColor(R.styleable.ViewCardProfile_buttonColor, 0).let { color ->
                if (color != 0) {
                    binding.buttonEdit.setBackgroundColor(color)
                }
            }
        }
    }

}