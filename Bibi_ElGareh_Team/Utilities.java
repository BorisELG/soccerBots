package Bibi_ElGareh_Team;
import	EDU.gatech.cc.is.util.Vec2;
import EDU.gatech.cc.is.util.Units;

public class Utilities {

    public static Vec2 getDistance(Vec2 v1, Vec2 v2) {
        Vec2 tmp=new Vec2(v1);
        tmp.sub(v2);
        return tmp;
    }

    public static Vec2 getAbsolutePosition(Vec2 obj, Vec2 position) {
        Vec2 tmp=new Vec2(obj);
        tmp.add(position);
        return tmp;
    }

    public static boolean equalsPosition(Vec2 obj1, Vec2 obj2, double epsilon) {
        return  (obj1.x<obj2.x+epsilon && obj1.x>obj2.x-epsilon
                && obj1.y < obj2.y+epsilon && obj1.y > obj2.y-epsilon);
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