package com.clickygame;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

/**
 * Created by prashant.patel on 8/30/2017.
 */

public class ActViewSingle extends Activity
{

    ImageView ivPhoto;
    FloatingActionButton fab;

    Bitmap oraginal = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.act_view_single);

        ivPhoto = (ImageView) findViewById(R.id.ivPhoto);
        fab = (FloatingActionButton) findViewById(R.id.fab);

        oraginal = BitmapFactory.decodeResource(getApplicationContext().getResources(),
                R.drawable.download);
        ivPhoto.setImageBitmap(oraginal);

       /* int width = oraginal.getWidth();
        int height = oraginal.getHeight();
        int length = width * height;
        int[] pixels = new int[width*height];
        oraginal.getPixels(pixels, 0, width, 0, 0, width, height);

        for (int i = 0; i < length; i++) {
            if (filter(pixels[i])) {
                pixels[i] = Color.rgb(0, 0, 0);
            } else pixels[i] = Color.rgb(255, 255, 255);
        }

        oraginal.setPixels(pixels, 0, width, 0, 0, width, height);
        ivPhoto.setImageBitmap(oraginal);
*/

      /*  runOnUiThread(new Runnable() {
            @Override
            public void run() {
                int[] pix = new int[oraginal.getWidth() * oraginal.getHeight()];

                Bitmap bitmap = oraginal.copy(oraginal.getConfig(), true);
                bitmap.getPixels(pix, 0, bitmap.getWidth(), 0, 0,bitmap.getWidth(), bitmap.getHeight());
                filterPixels(bitmap.getWidth(), bitmap.getHeight(), pix);

                bitmap.setPixels(pix, 0, bitmap.getWidth(), 0, 0,bitmap.getWidth(), bitmap.getHeight());
                ivPhoto.setImageBitmap(bitmap);

            }
        });*/

    }

    int range = 100;
    protected int[] filterPixels( int width, int height, int[] inPixels )
    {
        int levels = 256;
        int index = 0;

        int[] rHistogram = new int[levels];
        int[] gHistogram = new int[levels];
        int[] bHistogram = new int[levels];
        int[] rTotal = new int[levels];
        int[] gTotal = new int[levels];
        int[] bTotal = new int[levels];
        int[] outPixels = new int[width * height];

        for (int y = 0; y < height; y++)
        {
            for (int x = 0; x < width; x++)
            {
                for (int i = 0; i < levels; i++)
                    rHistogram[i] = gHistogram[i] = bHistogram[i] = rTotal[i] = gTotal[i] = bTotal[i] = 0;

                for (int row = -range; row <= range; row++)
                {
                    int iy = y+row;
                    int ioffset;
                    if (0 <= iy && iy < height)
                    {
                        ioffset = iy*width;
                        for (int col = -range; col <= range; col++)
                        {
                            int ix = x+col;
                            if (0 <= ix && ix < width) {
                                int rgb = inPixels[ioffset+ix];
                                int r = (rgb >> 16) & 0xff;
                                int g = (rgb >> 8) & 0xff;
                                int b = rgb & 0xff;
                                int ri = r*levels/256;
                                int gi = g*levels/256;
                                int bi = b*levels/256;
                                rTotal[ri] += r;
                                gTotal[gi] += g;
                                bTotal[bi] += b;
                                rHistogram[ri]++;
                                gHistogram[gi]++;
                                bHistogram[bi]++;
                            }
                        }
                    }
                }

                int r = 0, g = 0, b = 0;
                for (int i = 1; i < levels; i++)
                {
                    if (rHistogram[i] > rHistogram[r])
                        r = i;
                    if (gHistogram[i] > gHistogram[g])
                        g = i;
                    if (bHistogram[i] > bHistogram[b])
                        b = i;
                }
                r = rTotal[r] / rHistogram[r];
                g = gTotal[g] / gHistogram[g];
                b = bTotal[b] / bHistogram[b];
                outPixels[index] = (inPixels[index] & 0xff000000) | ( r << 16 ) | ( g << 8 ) | b;
                index++;
            }
        }

        return outPixels;
    }
}
