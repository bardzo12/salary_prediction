/**
 * Created by Tomáš Baránek on 30.3.2018.
 * FIIT STUBA
 * tomas.baranek1994@gmail.com
 */
public class Cluster {
    public float lat;
    public float lng;
    public int cluster;

    public Cluster(float lat, float lng, int cluster) {
        this.lat = lat;
        this.lng = lng;
        this.cluster = cluster;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cluster cluster = (Cluster) o;

        if (Float.compare(cluster.lat, lat) != 0) return false;
        return Float.compare(cluster.lng, lng) == 0;
    }

    @Override
    public int hashCode() {
        int result = (lat != +0.0f ? Float.floatToIntBits(lat) : 0);
        result = 31 * result + (lng != +0.0f ? Float.floatToIntBits(lng) : 0);
        return result;
    }
}
