package com.example.week5

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.now_playing.*
import java.io.IOException
import okhttp3.*
import com.google.gson.GsonBuilder
import android.os.AsyncTask.execute
import android.support.v4.app.FragmentActivity
import com.google.gson.Gson
import android.support.v7.widget.RecyclerView
import android.text.Layout


class nowPlayingFragment : Fragment() {
    lateinit var movieAdapter: MovieAdapter
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.now_playing, container, false)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val client = OkHttpClient()
        val request = Request.Builder()
            .url("https://api.themoviedb.org/3/movie/now_playing?api_key=7519cb3f829ecd53bd9b7007076dbe23")
            .build()

        client.newCall(request)
            .enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    getActivity()?.runOnUiThread(Runnable {
                        print("nothing")
                    })

                }

                override fun onResponse(call: Call, response: Response) {
                    val json = response.body()!!.string()
                    val movies = Gson().fromJson(json, MovieModel.ResultArray::class.java)
                    print(movies)
                    getActivity()?.runOnUiThread(Runnable {
                        rvMovies.apply {
                            layoutManager = LinearLayoutManager(context)
                            movieAdapter = MovieAdapter(movies.results, context)
                            adapter = movieAdapter
                            movieAdapter.setListener(MovieItemClickListener)
                        }
                    })

                }

            })
    }
    private val MovieItemClickListener = object : MovieItemClickListener {
        override fun onItemCLicked(position: Int) {

            print("Item Clicked")

        }
        override fun onItemLongCLicked(position: Int) {
            print("Item Long Click")

        }
    }
}

//fun main(){
//    var ts = nowPlayingFragment()
//    ts.getData()
//}

