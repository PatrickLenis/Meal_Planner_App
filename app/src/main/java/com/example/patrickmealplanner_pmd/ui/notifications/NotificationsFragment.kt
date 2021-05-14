package com.example.patrickmealplanner_pmd.ui.notifications

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.patrickmealplanner_pmd.LoginActivity
import com.example.patrickmealplanner_pmd.R

class NotificationsFragment : Fragment() {

    private lateinit var notificationsViewModel: NotificationsViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?

    ): View? {

        notificationsViewModel =
                ViewModelProvider(this).get(NotificationsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_notifications, container, false)
        val textView: TextView = root.findViewById(R.id.text_notifications)

        //sign out
        val sign_out = Intent(activity, LoginActivity::class.java)
        val sign_out_button = root.findViewById<Button>(R.id.sign_out_button)
        sign_out_button.setOnClickListener { startActivity(sign_out) }

        //themes

        //texts
        val default_text = root.findViewById<TextView>(R.id.default_text)
        val dark_text = root.findViewById<TextView>(R.id.dark_text)
        val light_text = root.findViewById<TextView>(R.id.light_text)

        //buttons
        val default_button = root.findViewById<ImageView>(R.id.button_default)
        val dark_button = root.findViewById<ImageView>(R.id.button_dark)
        val light_button = root.findViewById<ImageView>(R.id.button_light)

        //funcctions
        fun switchDefault()
        {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
            //buttons
            /*default_button.setImageResource(R.drawable.app_button_left)
            dark_button.setImageResource(R.drawable.app_button_mid_inactive)
            light_button.setImageResource(R.drawable.app_button_right_inactive)*/
        }

        fun switchDark()
        {
            //buttons
            /*default_button.setImageResource(R.drawable.app_button_left_inactive)
            dark_button.setImageResource(R.drawable.app_button_mid)
            light_button.setImageResource(R.drawable.app_button_right_inactive)*/

            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }

        fun switchLight()
        {
            //buttons
            /*default_button.setImageResource(R.drawable.app_button_left_inactive)
            dark_button.setImageResource(R.drawable.app_button_mid_inactive)
            light_button.setImageResource(R.drawable.app_button_right)*/

            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        }
        //theme swithc events
        default_text.setOnClickListener { switchDefault() }
        dark_text.setOnClickListener { switchDark() }
        light_text.setOnClickListener { switchLight() }

        notificationsViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }

}