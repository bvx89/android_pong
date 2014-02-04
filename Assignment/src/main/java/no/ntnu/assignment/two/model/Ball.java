package no.ntnu.assignment.two.model;

import no.ntnu.assignment.two.wall.LeftWall;
import no.ntnu.assignment.two.wall.RightWall;
import no.ntnu.assignment.two.wall.Wall;
import sheep.game.Sprite;
import sheep.graphics.Image;
import sheep.math.Vector2;

/**
 * Created by oknak_000 on 2/4/14.
 */
public class Ball extends Sprite {
    private int mHeight;
    public Ball(Image image, int size, int x, int y, int mHeight){
        super(image);
        setScale(size,size);
        setPosition(x,y);
        this.mHeight = mHeight;
    }

    public void handleWallCollision(Wall wall){
        if(getX() > wall.getX() && getSpeed().getX() < 0 ||
           getX() < wall.getX() && getSpeed().getX() > 0){
            setSpeed(-getSpeed().getX(), getSpeed().getY());
        }
    }

    public void handlePaddleCollision(Paddle paddle){
        // Moves paddle center up or down according to what paddle the ball hits
        float addedCircleCenter = paddle.getScale().getX() / 4;
        if (paddle.getY() > mHeight / 2)
            addedCircleCenter = -addedCircleCenter;

        // Handles collision like paddle is a ball, with center paddle.width / 4 lower than it's actual center to create more interesting ball paths
        Vector2 distance = new Vector2((getX() + getScale().getX() / 2) - (paddle.getX() + paddle.getScale().getX() / 2),
                (getY() + getScale().getY() / 2) - (paddle.getY() + paddle.getScale().getY() / 2) + addedCircleCenter);

        // Sets new velocity vector, but keeps the same total speed.
        float length = distance.getLength();
        setSpeed(distance.getX() / length * getSpeed().getLength(),
                distance.getY() / length * getSpeed().getLength());
    }

    @Override
    public boolean collides(Sprite other){
        if(other.getBoundingBox().intersects(getBoundingBox())){
            // Left Wall Collision
            if (other instanceof LeftWall){
                if(getSpeed().getX() < 0)
                    return true;
                else
                    return false;
            // Right Wall Collision
            }else if(other instanceof RightWall){
                if(getSpeed().getX() > 0)
                    return true;
                else
                    return false;
            }else if (other instanceof Paddle){
                // Top Paddle Collision
                if((Paddle)other.isOnTop()){
                    if (getSpeed().getY() < 0)
                        return true;
                    else
                        return false;
                // Bottom Paddle Collision
                }else{
                    if (getSpeed().getY() > 0)
                        return true;
                    else
                        return false;
                }
            }else{
                return true;
            }
        }else{
            return false;
        }
    }


}