package info.epifit.imageslideshow

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import info.epifit.imageslider.ImageSlider
import info.epifit.imageslider.constants.ActionTypes
import info.epifit.imageslider.constants.ScaleTypes
import info.epifit.imageslider.interfaces.ItemChangeListener
import info.epifit.imageslider.interfaces.ItemClickListener
import info.epifit.imageslider.interfaces.TouchListener
import info.epifit.imageslider.models.SlideModel

/**
 * Created by Deniz Coşkun on 6/23/2020.
 * denzcoskun@hotmail.com
 * İstanbul
 */

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val imageSlider = findViewById<ImageSlider>(R.id.image_slider) // init imageSlider

        val imageList = ArrayList<SlideModel>() // Create image list
        imageList.add(SlideModel("https://bit.ly/37Rn50u", "Baby Owl",ScaleTypes.CENTER_CROP))
        imageList.add(SlideModel("https://bit.ly/2BteuF2", "Elephants and tigers may become extinct."))
        imageList.add(SlideModel("https://bit.ly/3fLJf72", "The population of elephants is decreasing in the world."))

        imageSlider.setImageList(imageList)

        imageSlider.setItemClickListener(object : ItemClickListener {
            override fun onItemSelected(position: Int) {
                // You can listen here.
            }
        })

        imageSlider.setItemChangeListener(object : ItemChangeListener {
            override fun onItemChanged(position: Int) {
                //println("Pos: " + position)
            }
        })

        imageSlider.setTouchListener(object : TouchListener {
            override fun onTouched(touched: ActionTypes) {
                if (touched == ActionTypes.DOWN){
                    imageSlider.stopSliding()
                } else if (touched == ActionTypes.UP ) {
                   imageSlider.startSliding(1000)
                }
            }
        })
    }

}
