package info.epifit.imageslider.models

import info.epifit.imageslider.constants.ScaleTypes

/**
 * Created by Deniz Coşkun on 6/23/2020.
 * denzcoskun@hotmail.com
 * İstanbul
 */
class SlideModel {

    var imageUrl: String? = null
    var imagePath: Int? = 0
    var title: String? = null
    var scaleType: ScaleTypes? = null
    var like: Boolean = false

    constructor(imageUrl: String?, title: String? = null, scaleType: ScaleTypes?  = null) {
        this.imageUrl = imageUrl
        this.title = title
        this.scaleType = scaleType
    }

    constructor(imagePath: Int?, title: String?  = null, scaleType: ScaleTypes?  = null) {
        this.imagePath = imagePath
        this.title = title
        this.scaleType = scaleType
    }

    constructor(imageUrl: String?, scaleType: ScaleTypes?) {
        this.imageUrl = imageUrl
        this.scaleType = scaleType
    }

    constructor(imagePath: Int?, scaleType: ScaleTypes?) {
        this.imagePath = imagePath
        this.scaleType = scaleType
    }
}