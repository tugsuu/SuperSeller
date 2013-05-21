package mn.aurora.seller;

import android.R.color;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter{

	private Context mContext;
	
	public ImageAdapter(Context c){
		this.mContext = c;
	}
	public int getCount() {
		// TODO Auto-generated method stub
		return mThumbIds.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ImageView imageView;
        if (convertView == null) {  // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(110, 110));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setBackgroundColor(color.background_dark);
            imageView.setPadding(4, 4, 4, 4);
        } else {
            imageView = (ImageView) convertView;
        }

        imageView.setImageResource(mThumbIds[position]);
        return imageView;
	}
	
	 private Integer[] mThumbIds = {
	            R.drawable.pic_1, R.drawable.pic_2,
	            R.drawable.pic_3, R.drawable.pic_4,
	            R.drawable.pic_5, R.drawable.pic_6,
//			    R.drawable.main1, R.drawable.main2,
//	            R.drawable.main3, R.drawable.main4,
//	            R.drawable.main5, R.drawable.main6,
	            
	         
	    };

	
}
