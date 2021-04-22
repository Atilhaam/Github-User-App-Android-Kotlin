package com.ilham.githubuserapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.CompoundButton
import com.ilham.githubuserapp.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity(), CompoundButton.OnCheckedChangeListener {
    private lateinit var binding : ActivitySettingsBinding
    private lateinit var mUserPreference: UserPreference
    private lateinit var alarmReciever: AlarmReciever
    private lateinit var alarmModel: AlarmModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = "Settings"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        alarmReciever = AlarmReciever()
        mUserPreference = UserPreference(this)
        binding.switch1.isChecked = mUserPreference.getPreference().isChecked

        binding.switch1.setOnCheckedChangeListener(this)
    }

    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
        if (isChecked) {
            savePreference(true)
            alarmReciever.setRepeatingAlarm(this, "Repeating", "09:00", "Find Your Favorite Github User RightNow!!!")
        } else {
            savePreference(false)
            alarmReciever.cancelRepeatingAlarm(this)
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun savePreference(isChecked: Boolean) {
        val userPreference = UserPreference(this)
        alarmModel = AlarmModel()
        alarmModel.isChecked = isChecked
        userPreference.setPreference(alarmModel)
    }


}