package ru.distance;

import javax.annotation.ManagedBean;
import javax.enterprise.context.ApplicationScoped;

@ManagedBean
@ApplicationScoped
public class LevenshteinDistance implements Distance {

    public LevenshteinDistance() { }

	@Override
	public int getDistance(String string1, String string2) {
		
		int distance[][];
        int n, m, i, j;
        char s_i, t_j;
        int cost;

        n = string1.length();
        m = string2.length();
        if (n == 0) {
            return m;
        }
        if (m == 0) {
            return n;
        }
        distance = new int[n+1][m+1];

        for (i = 0; i <= n; i++) {
            distance[i][0] = i;
        }
        for (j = 0; j <= m; j++) {
            distance[0][j] = j;
        }

        for (i = 1; i <= n; i++)
        {
            s_i = string1.charAt (i - 1);

            for (j = 1; j <= m; j++)
            {
                t_j = string2.charAt(j - 1);
                if (s_i == t_j) {
                    cost = 0;
                }
                else {
                    cost = 1;
                }
                distance[i][j] = findMinimum(distance[i-1][j]+1, distance[i][j-1]+1, distance[i-1][j-1] + cost);
            }
        }

        return distance[n][m];
	}
	
	private int findMinimum(int a, int b, int c) {
        int min = a;
        if (b < min) {
            min = b;
        }
        if (c < min) {
            min = c;
        }
        return min;
    }

}
