package bibiElGarehTeam;
import	EDU.gatech.cc.is.util.Vec2;
import EDU.gatech.cc.is.util.Units;


/**
 * Contient uniquement des méthodes statiques permettant
 * tous les calculs vectoriels
 */
public class Utilities {

    /**
     * Calcul la distance entre deux vecteurs
     * @param v1
     * @param v2
     * @return Vec2
     */
    public static Vec2 getDistance(Vec2 v1, Vec2 v2) {
        Vec2 tmp=new Vec2(v1);
        tmp.sub(v2);
        return tmp;
    }

    /**
     * Calcul la position absolue entre deux vecteurs
     * @param v1
     * @param position
     * @return Vec2
     */
    public static Vec2 getAbsolutePosition(Vec2 v1, Vec2 position) {
        Vec2 tmp=new Vec2(v1);
        tmp.add(position);
        return tmp;
    }

    /**
     * Méthode qui vérifie si deux vecteurs on la même position, en prenant compte
     * un écart plus ou moins grand
     * @param v1
     * @param v2
     * @param epsilon
     * @return vrai si deux vecteurs ont la même position
     */
    public static boolean equalsPosition(Vec2 v1, Vec2 v2, double epsilon) {
        return  (v1.x<v2.x+epsilon && v1.x>v2.x-epsilon
                && v1.y < v2.y+epsilon && v1.y > v2.y-epsilon);
    }

    /**
     * Normalise un angle dans l'interval [-PI,PI]
     * @param angle
     * @return l'angle
     */
    private static double normalizeZero(double angle)
    {
        while (angle>Math.PI)
            angle -= 2*Math.PI;
        while (angle<-Math.PI)
            angle += 2*Math.PI;				// range -PI .. PI
        return angle;
    }

    /**
     * Regarde si un vecteur est dans l'axe, selon un interval
     * @param axis
     * @param v1
     * @param interval
     * @return vrai, si l'objet est dans l'axe
     */
    public static boolean inAxis(double axis, Vec2 v1, double interval) {
        return Math.abs(normalizeZero(v1.t- axis)) < interval;
    }
}