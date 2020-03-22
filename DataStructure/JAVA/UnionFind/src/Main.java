import java.util.Random;

public class Main {
    private static double testUF(UF uf,int m){
        int size=uf.getSize();
        Random random=new Random();

        long startTime=System.nanoTime();

        for (int i = 0; i < m; i++) {
            int a=random.nextInt(size);
            int b=random.nextInt(size);
            uf.unionElements(a,b);
        }

        for(int i=0;i<m;i++){
            int a=random.nextInt(size);
            int b=random.nextInt(size);
            uf.isConnected(a,b);
        }

        long endTime=System.nanoTime();

        return (endTime-startTime)/1000000000.0;
    }
    public static void main(String[] args) {
	    int size=50000000;
	    int m=50000000;
//	    UnionFind1 uf1=new UnionFind1(size);
//        System.out.println("UnionFind1: "+testUF(uf1,m)+"s");
//
//        UnionFind2 uf2=new UnionFind2(size);
//        System.out.println("UnionFind2: "+testUF(uf2,m)+"s");

        UnionFind3 uf3=new UnionFind3(size);
        System.out.println("基于数量的UnionFind3: "+testUF(uf3,m)+"s");

        UnionFind4 uf4=new UnionFind4(size);
        System.out.println("无压缩的UnionFind4: "+testUF(uf4,m)+"s");

        UnionFind5 uf5=new UnionFind5(size);
        System.out.println("一般压缩的UnionFind5: "+testUF(uf5,m)+"s");

        UnionFind6 uf6=new UnionFind6(size);
        System.out.println("2层的UnionFind6: "+testUF(uf6,m)+"s");

        UnionFind myuf=new UnionFind(size);
        System.out.println("UnionFind: "+testUF(myuf,m)+"s");


    }
}
