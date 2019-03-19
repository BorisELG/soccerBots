package Bibi_ElGareh_Team;
import	EDU.gatech.cc.is.util.Vec2;
import EDU.gatech.cc.is.util.Units;

public class Utilities {

    public static Vec2 getDistance(Vec2 v1, Vec2 v2) {
        Vec2 tmp=new Vec2(v1);
        tmp.sub(v2);
        return tmp;
    }


    /**
     *  Normalize an angle into the range [-PI,PI]
    */
    private static double normalizeZero(double angle)
    {
        while (angle>Math.PI)
            angle -= 2*Math.PI;
        while (angle<-Math.PI)
            angle += 2*Math.PI;				// range -PI .. PI
        return angle;
    }

    public static boolean inAxis(double axis, Vec2 object, double interval) {
        return Math.abs(normalizeZero(object.t- axis)) < interval;
    }
}