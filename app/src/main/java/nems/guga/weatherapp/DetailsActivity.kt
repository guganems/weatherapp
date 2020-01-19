package nems.guga.weatherapp

import android.content.Intent
import android.net.Uri
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_details.*
import org.json.JSONObject
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*

class DetailsActivity : AppCompatActivity() {

    var CITY: String = "Tbilisi,GE"
    val API: String = "16d01da9c5125edd9cc8380c6c50658e"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        weatherTask().execute()
        roast.setOnClickListener {
            val url = "https://www.reddit.com/r/RoastMe/comments/er09np/this_is_my_android_project_in_the_university_for/"

            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            startActivity(intent)
        }
    }

    inner class weatherTask() : AsyncTask<String, Void, String>() {
        override fun onPreExecute() {
            super.onPreExecute()
        }

        override fun doInBackground(vararg params: String?): String? {
            var response:String?
            try{
                response = URL("https://api.openweathermap.org/data/2.5/forecast?q=$CITY&units=metric&APPID=$API").readText(
                    Charsets.UTF_8
                )
            }catch (e: Exception){
                response = null
            }
            return response
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            try {
                /* Extracting JSON returns from the API */
                val jsonObj = JSONObject(result)
                val list = jsonObj.getJSONArray("list")
                val first = list.getJSONObject(0)
                val second = list.getJSONObject(8)
                val third = list.getJSONObject(16)
                val fourth = list.getJSONObject(24)
                val temp1 = first.getJSONObject("main").getString("temp") + "°C"
                val temp2 = second.getJSONObject("main").getString("temp") + "°C"
                val temp3 = third.getJSONObject("main").getString("temp") + "°C"
                val temp4 = fourth.getJSONObject("main").getString("temp") + "°C"
                val upd1 = first.getLong("dt")
                val upd2 = second.getLong("dt")
                val upd3 = third.getLong("dt")
                val upd4 = fourth.getLong("dt")
                val date1 = SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.ENGLISH).format(
                    Date(upd1*1000)
                )
                val date2 = SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.ENGLISH).format(
                    Date(upd2*1000)
                )
                val date3 = SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.ENGLISH).format(
                    Date(upd3*1000)
                )
                val date4 = SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.ENGLISH).format(
                    Date(upd4*1000)
                )

                next_date_1.text = date1
                next_date_2.text = date2
                next_date_3.text = date3
                next_date_4.text = date4
                next_weather_1.text = temp1
                next_weather_2.text = temp2
                next_weather_3.text = temp3
                next_weather_4.text = temp4
            } catch (e: Exception) {
                findViewById<ProgressBar>(R.id.loader).visibility = View.GONE
                findViewById<TextView>(R.id.errorText).visibility = View.VISIBLE
            }

        }
    }
}
