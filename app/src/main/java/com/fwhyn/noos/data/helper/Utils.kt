package com.fwhyn.noos.data.helper

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.Random

object Utils {
    var vibrantLightColorList = arrayOf(
        ColorDrawable(Color.parseColor("#ffeead")),
        ColorDrawable(Color.parseColor("#93cfb3")),
        ColorDrawable(Color.parseColor("#fd7a7a")),
        ColorDrawable(Color.parseColor("#faca5f")),
        ColorDrawable(Color.parseColor("#1ba798")),
        ColorDrawable(Color.parseColor("#6aa9ae")),
        ColorDrawable(Color.parseColor("#ffbf27")),
        ColorDrawable(Color.parseColor("#d93947"))
    )
    val randomDrawableColor: ColorDrawable
        get() {
            val index = Random().nextInt(vibrantLightColorList.size)
            return vibrantLightColorList[index]
        }

    //    public static String DateToTimeFormat(String oldstringDate){
    //        PrettyTime p = new PrettyTime(new Locale(getCountry()));
    //        String isTime = null;
    //        try {
    //            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'",
    //                    Locale.ENGLISH);
    //            Date date = sdf.parse(oldstringDate);
    //            isTime = p.format(date);
    //        } catch (ParseException e) {
    //            e.printStackTrace();
    //        }
    //
    //        return isTime;
    //    }

    fun getNewDateFormat(oldDate: String): String {
        return try {
            val oldDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale(country))
            val parsedOldDate = oldDateFormat.parse(oldDate)
            val newDateFormat = SimpleDateFormat("E, d MMM yyyy", Locale(country))

            newDateFormat.format(parsedOldDate!!)
        } catch (e: Exception) {
            e.printStackTrace()

            oldDate
        }
    }

    val country: String
        get() {
            val locale = Locale.getDefault()
            val country = locale.country.toString()

            return country.lowercase(Locale.getDefault())
        }

    val language: String
        get() {
            val locale = Locale.getDefault()
            val country = locale.language.toString()

            return country.lowercase(Locale.getDefault())
        }
}

fun <T : java.io.Serializable> Intent.getSerializable(key: String, clazz: Class<T>): T? {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        this.getSerializableExtra(key, clazz)
    } else {
        this.getSerializableExtra(key) as? T
    }
}