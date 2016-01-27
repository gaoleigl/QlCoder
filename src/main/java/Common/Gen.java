package Common;

/**
 * Created by gaolei on 16/1/25.
 */
public class Gen {
    private static final long twist(long u, long v) {
        return (((u & 0x80000000L) | (v & 0x7fffffffL)) >> 1) ^ ((v & 1) == 1 ? 0x9908b0dfL : 0);
    }
    private long[] state= new long[624];
    private int left = 1;
    public Gen() {
        for (int j = 1; j < 624; j++) {
            state[j] = (1812433253L * (state[j - 1] ^ (state[j - 1] >> 30)) + j);
            state[j] &= 0xffffffffL;
        }
    }
    public void next_state() {
        int p = 0;
        left = 624;
        for (int j = 228; --j > 0; p++)
            state[p] = state[p+397] ^ twist(state[p], state[p + 1]);

        for (int j=397;--j>0;p++)
            state[p] = state[p-227] ^ twist(state[p], state[p + 1]);

        state[p] = state[p-227] ^ twist(state[p], state[0]);
    }

    public long next() {
        if (--left == 0) next_state();
        return state[624-left];
    }
    public static void main(String[] args)
    {
        Gen rand=new Gen();
        for(int i=0;i<10000000;i++)
        {
            for(int j=0;j<40;j++)
            {
                System.out.print(rand.next()%2);
            }
            System.out.println();
        }
    }
}
