package com.example.insider1.pingpong;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

/**
 * Created by hassan on 4/14/17.
 */

public class DesignClass extends View {

    //Ball Variables

    float x_pos =100;
    float y_pos = 100;
    float recx = 0;
    float x1=0;
    float y1=0;
    float h1=0;
    float w1 =0;

    Paint p, p2;
    int count = 1;
    int radius = 40;
    int padding_p1 = 0;
    int padding_p2 = 0;
    float factorX=3;
    float factorY = 3;
    int height, width;
    int score1 = 0, score2 = 0;
    float right = 1, down = 1, accelerator = 1;

    public DesignClass(Context context, @Nullable AttributeSet attrs) {

        super(context, attrs);


    }


    @Override
    public boolean onTouchEvent(MotionEvent e) {

        float x = e.getX();
        float y = e.getY();

//        if(y< height/4){
//            reset();
//        }
        if(x>0 && x< width/6 && y> height/2 - height/25 && y < height/2 + height/25){
restart();
        }

        else if(x > width/2 && y < height/2 && y_pos < height / 2 ){ //p1 right
            padding_p1 += 30;
            if(padding_p1+width/6 > width){
                padding_p1 = width-width/6;
            }
        }
        else if(x < width/2 && y < height/2 && y_pos < height / 2) {//p1 left
            padding_p1 -= 30;
            if(padding_p1<0){
                padding_p1 = 0;
            }
        }
        else if(x > width/2 && y > height/2 && y_pos > height / 2 ) {//p1 right
            padding_p2 += 30;
            if(padding_p2+width/6 > width ){
                padding_p2 = width-width/6;
            }
        }
        else if(x < width/2 && y > height/2 && y_pos > height / 2) {//p1 left
            padding_p2 -= 30;
            if(padding_p2<0){
                padding_p2 = 0;
            }
        }

//        switch (e.getAction() & MotionEvent.ACTION_MASK) {
//            case MotionEvent.ACTION_MOVE:
//                if (x1 < x) {
//                    recx += 10;
//                } else {
//                    recx -= 10;
//                }
//                break;
//            case MotionEvent.ACTION_DOWN:
//                if (x1 < x) {
//                    recx += 10;
//                } else {
//                    recx -= 10;
//                }
//                break;
//        }


//        postInvalidate();
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {

        super.onDraw(canvas);




        if(count == 1){//runs only forst time....
            width = canvas.getWidth();
            height = canvas.getHeight();
            p = new Paint();
            p.setARGB(255, 255, 255, 255);
            p2 = new Paint();
            p2.setTextSize(48f);
            p2.setARGB(255,0,0,0);
            radius = height/50;//will set it self up for smaller devices...
            resetPos();

            start();

            count--;
        }



//      p.setColor(Color.rgb(255,0,0));
        //int w = canvas.getWidth(), h = canvas.getHeight();





//        //Upper Rect Pos
//        float x = getLeft()+(getRight()-getLeft())/6;
//        float y= getRight()-(getRight()-getLeft())/4;
//
//
//        canvas.drawRect(
//                x,
//                getTop()+20,
//                y,
//                getTop()+70,p);


        //Lower Rect
//        x1 = getLeft()+(getRight()-getLeft())/4 + recx;
//        y1= getRight()-(getRight()-getLeft())/3 + recx;
//        h1 =  getTop()+(getBottom() - 300);
//        w1 = getBottom()-100;

//            canvas.drawRect(
//                    x1,
//                    h1,
//                    y1,
//                    w1,
//                    p);

        canvas.drawLine(0, height-height/30, width, height-height/30, p2);
        canvas.drawLine(0, height/30, width, height/30, p2);
        canvas.drawLine(0, height/2, width, height/2, p2);
        canvas.drawRect(//player one bar
                padding_p1, 0, padding_p1+width/6, height/30, p2
        );
        canvas.drawRect(//player two bar
                padding_p2, height -height/30, padding_p2+width/6, height, p2
        );

        canvas.drawRect(//player two bar
                0, height/2 - height/25, width/6, height/2+height/25, p
        );


//        Log.e("msg", ""+factorX+" "+factorY+" "+down);
//



        x_pos = x_pos + factorX;
        y_pos = y_pos + factorY;

        canvas.drawCircle(x_pos,y_pos,radius,p2);


//        if (x_pos>=canvas.getWidth() || x_pos<=5)
//        {
//            factorX=-1*factorX;}
//        if (y_pos<=5)
//        {
//            factorY=-1*factorY;
//        }
//        //y_pos>=canvas.getHeight()
//
//        if((x_pos>=x1 && x_pos<=y1 ) && (y_pos>=h1 && y_pos<=w1 )){
//            factorX=-1*factorX;
//            factorY=-1*factorY;
//        }

        if(x_pos + radius >= width || x_pos - radius <= 0){
            right *= -1;
            factorX *= right;
        }

        if(y_pos +radius >= height-(height/30) || y_pos-radius <= height/30){

            factorY *= down;
            down *= -1;

            //Stack q = new Stack();
            factorX *= 1.02;
            factorY *= 1.02;


            if((x_pos > padding_p1+width/6 || x_pos <padding_p1)  && y_pos < height/2){
                resetPos();
                start();
                factorY *= down;
                //down *= -1;
                score2++;

            }
            else if((x_pos > padding_p2+width/6 || x_pos < padding_p2) && y_pos>height/2 ){
                resetPos();
                start();
                factorY *= down;
                //down *= -1;
                score1++;

            }

        }

        canvas.drawText(""+score1, width/2, height/4, p2);
        canvas.drawText(""+score2, width/2, height*3/4, p2);

        invalidate();

    }

    void resetPos(){

        x_pos = width/2;
        y_pos = height/2;
    }


    void start(){
        factorX = (int)(Math.random()*5) +4;
        factorY = (int)(Math.random()*5 ) +4;

        int num = (int)(Math.random()*4 ) +1;
        if(num%2 != 0){
            right *=-1;
        }
//        String msg = ""+factorX+" "+factorY +" "+num;
//        Toast t = Toast.makeText(getContext(),msg , Toast.LENGTH_SHORT);
//        t.show();

    }

    void restart(){
        resetPos();
        start();
        score1 = 0;
        score2 = 0;
    }
}
