自定义评分条-CustomRatingBar
---------
#### 功能特性
1.可设置星星大小  
2.可设置星星之间的间距  
3.可以设置星星图片（填充图片和未填充图片）  
4.可以设置星星是否可触摸评分  
5.可设置评分范围（整颗 | 半颗 | 随意）  
6.可以设置总星量

自定义评分条-CustomAnimRatingBar（二）
---------
#### 功能特性
1.可设置星星大小  
2.可设置星星之间的间距  
3.可以设置星星图片（填充图片和半填充图片）  
4.可以设置星星是否可触摸评分  
5.可设置评分范围（整颗 | 半颗 ）  **此处不支持随意**  
6.可以设置总星量  
7.支持动画效果

![这里写图片描述](http://img.blog.csdn.net/20170727111945379?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvc2RmZHp4/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)

#### Usage
##### CustomAnimRatingBar
    <com.study.dzx.library.widget.CustomAnimRatingBar
        android:layout_below="@+id/tv_tag_anim"
        android:layout_centerHorizontal="true"
        android:id="@+id/rb_star"
        android:layout_width="wrap_content"
        android:layout_height="45dp"
        app:modeAnim="half"
        app:starAnimFill="@mipmap/star_big_fill"
        app:starAnimDistance="10dp"
        app:starAnimEmpty="@mipmap/star_big_empty"
        app:starAnimHalf="@mipmap/star_big_half"
        app:starAnimNum="5"
        app:starAnimSize="35dp" />
        
##### CustomRatingBar
    <com.study.dzx.library.widget.CustomRatingBar
        android:layout_below="@+id/tv_tag_noanim"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        app:starNum="5"
        app:starDistance="15dp"
        app:starSize="120px"
        app:starEmpty="@mipmap/empty"
        app:starFill="@mipmap/fill"
        app:mode="random"
        app:touchAble="true"
/>
