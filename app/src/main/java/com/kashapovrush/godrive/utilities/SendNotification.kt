package com.kashapovrush.godrive.utilities

import android.content.Context
import android.os.StrictMode
import android.util.Log
import com.android.volley.*
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject


class SendNotification {
    companion object {
        const val SERVER_KEY =
            "key=AAAAL0phi1A:APA91bHeo-CCtybN9v3oVOUqox_0h44CkdzV9QN-8uEWqBR312dj1Te5U0HC8HJ6WMc_M-lLsQBtNo6QdVScW286Pov1wbBVbsi9iS_Dw7T8dRo0KEMrxwYWi3SrvIxhcuw3DTa1WJbK"
        const val BASE_URL = "https://fcm.googleapis.com/fcm/send"

        fun pushNotification(context: Context, token: String, title: String, message: String) {
            val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
            StrictMode.setThreadPolicy(policy)

            val queue: RequestQueue = Volley.newRequestQueue(context)

            try {
                val json = JSONObject()
                json.put("to", token)
                val notification = JSONObject()
                notification.put("title", title)
                notification.put("body", message)
                json.put("notification", notification)

                val jsonObjectRequest = object: JsonObjectRequest(Request.Method.POST, BASE_URL, json,
                    object : Response.Listener<JSONObject> {
                        override fun onResponse(response: JSONObject?) {

                        }
                    }, object : Response.ErrorListener {
                        override fun onErrorResponse(error: VolleyError?) {

                        }

                    }) {
                    override fun getHeaders(): MutableMap<String, String> {
                        val params: HashMap<String, String> = HashMap()
                        params.put("Content-Type", "application/json")
                        params.put("Authorization", SERVER_KEY)
                        return params
                    }
                }
                queue.add(jsonObjectRequest)
            } catch (e: Exception) {
                Log.i("RushSend", e.message.toString())
            }
        }
    }
}