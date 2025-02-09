package info.epifit.imageslider.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.*
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import com.squareup.picasso.Picasso
import info.epifit.imageslider.R
import info.epifit.imageslider.constants.ActionTypes
import info.epifit.imageslider.constants.ScaleTypes
import info.epifit.imageslider.interfaces.ItemClickListener
import info.epifit.imageslider.interfaces.TouchListener
import info.epifit.imageslider.models.SlideModel
import info.epifit.imageslider.transformation.RoundedTransformation

/**
 * Created by Deniz Coşkun on 6/23/2020.
 * denzcoskun@hotmail.com
 * İstanbul
 */
class ViewPagerAdapter(context: Context?,
                       imageList: List<SlideModel>,
                       private var radius: Int,
                       private var errorImage: Int,
                       private var placeholder: Int,
                       private var titleBackground: Int,
                       private var scaleType: ScaleTypes?,
                       private var textAlign: String) : PagerAdapter() {

    constructor(context: Context, imageList: List<SlideModel>, radius: Int, errorImage: Int, placeholder: Int, titleBackground: Int, textAlign: String) :
            this(context, imageList, radius, errorImage, placeholder, titleBackground, null, textAlign)

    var imageList: List<SlideModel>? = imageList
    private var layoutInflater: LayoutInflater? = context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater?

    private var itemClickListener: ItemClickListener? = null
    private var touchListener: TouchListener? = null

    override fun isViewFromObject(view: View, obj: Any): Boolean {
        return view == obj
    }

    override fun getCount(): Int {
        return imageList!!.size
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun instantiateItem(container: ViewGroup, position: Int): View{
        val itemView = layoutInflater!!.inflate(R.layout.pager_row, container, false)

        val imageView = itemView.findViewById<ImageView>(R.id.image_view)
        val linearLayout = itemView.findViewById<LinearLayout>(R.id.linear_layout)
        val textView = itemView.findViewById<TextView>(R.id.text_view)
        val likeView = itemView.findViewById<ImageView>(R.id.like_view)

        if (imageList!![position].like){
            likeView.setImageResource(R.drawable.ic_heart_sel)
        }else{
            likeView.setImageResource(R.drawable.ic_heart_unsel)
        }

        if (imageList!![position].title != null){
            textView.text = imageList!![position].title
            linearLayout.setBackgroundResource(titleBackground)
            textView.gravity = getGravityFromAlign(textAlign)
            linearLayout.gravity = getGravityFromAlign(textAlign)
        }else{
            linearLayout.visibility = View.INVISIBLE
        }

        // Image from url or local path check.
        val loader = if (imageList!![position].imageUrl == null){
            Picasso.get().load(imageList!![position].imagePath!!)
        }else{
            Picasso.get().load(imageList!![position].imageUrl!!)
        }

        // set Picasso options.
        if ((scaleType != null && scaleType == ScaleTypes.CENTER_CROP) || imageList!![position].scaleType == ScaleTypes.CENTER_CROP){
            loader.fit().centerCrop()
        } else if((scaleType != null && scaleType == ScaleTypes.CENTER_INSIDE) || imageList!![position].scaleType == ScaleTypes.CENTER_INSIDE){
            loader.fit().centerInside()
        }else if((scaleType != null && scaleType == ScaleTypes.FIT) || imageList!![position].scaleType == ScaleTypes.FIT){
            loader.fit()
        }

        loader.transform(RoundedTransformation(radius, 0))
            .placeholder(placeholder)
            .error(errorImage)
            .into(imageView)

        container.addView(itemView)

        imageView.setOnClickListener{itemClickListener?.onItemSelected(position)}

        if (touchListener != null){
            imageView!!.setOnTouchListener { v, event ->
                when (event.action) {
                    MotionEvent.ACTION_MOVE -> touchListener!!.onTouched(ActionTypes.MOVE)
                    MotionEvent.ACTION_DOWN -> touchListener!!.onTouched(ActionTypes.DOWN)
                    MotionEvent.ACTION_UP -> touchListener!!.onTouched(ActionTypes.UP)
                }
                false
            }
        }

        return itemView
    }

    fun like(position: Int){
        imageList?.forEach { it.like = false }
        imageList?.get(position)?.like = true
    }

    override fun getItemPosition(`object`: Any): Int {
        return POSITION_NONE
    }

    /**
     * Get layout gravity value from textAlign variable
     *
     * @param  textAlign  text align by user
     */
    fun getGravityFromAlign(textAlign: String): Int {
        return when (textAlign) {
            "RIGHT" -> {
                Gravity.RIGHT
            }
            "CENTER" -> {
                Gravity.CENTER
            }
            else -> {
                Gravity.LEFT
            }
        }
    }

    /**
     * Set item click listener
     *
     * @param  itemClickListener  callback by user
     */
    fun setItemClickListener(itemClickListener: ItemClickListener) {
        this.itemClickListener = itemClickListener
    }

    /**
     * Set touch listener for listen to image touch
     *
     * @param  touchListener  interface callback
     */
    fun setTouchListener(touchListener: TouchListener) {
        this.touchListener = touchListener
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as RelativeLayout)
    }
}