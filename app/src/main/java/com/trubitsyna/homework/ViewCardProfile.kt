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
    lateinit var binding: ViewCardProfileBinding

    init {
        binding = ViewCardProfileBinding.inflate(
            LayoutInflater.from(context),
            this, true
        )

        context.withStyledAttributes(
            attrs,
            R.styleable.ViewCardProfile,
            defStyleAttr,
            0
        ) {
            getString(R.styleable.ViewCardProfile_title)?.let { title ->
                binding.textViewTitle.text = title
            }
            getString(R.styleable.ViewCardProfile_subtitle)?.let { subtitle ->
                binding.textViewSubtitle.text = subtitle
            }
            getDrawable(R.styleable.ViewCardProfile_profileImage)?.let { drawable ->
                binding.imageViewProfile.setImageDrawable(drawable)
            }
            getInt(R.styleable.ViewCardProfile_imageCounter, 0).let { num ->
                binding.textViewImageCounter.text = num.toString()
            }
            getInt(R.styleable.ViewCardProfile_subscriberCounter, 0).let { num ->
                binding.textViewSubscriberCounter.text = num.toString()
            }
            getInt(R.styleable.ViewCardProfile_postCounter, 0).let { num ->
                binding.textViewPostCounter.text = num.toString()
            }
            getColor(R.styleable.ViewCardProfile_buttonColor, 0)?.let { color ->
                binding.buttonEdit.setBackgroundColor(color)
            }
        }
    }

}