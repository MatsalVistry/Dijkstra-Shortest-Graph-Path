import java.io.*;
import java.util.*;

public class Dijkstra
{
    public static void main(String [] args) throws Exception
    {
        File file = new File("inputs.txt");

        Scanner sc = new Scanner(file);
        Hashtable<String,vertex> ht = new Hashtable<>();

        while(sc.hasNextLine())
        {
            String line = sc.nextLine();
            String [] arr = line.split(" ");

            if(ht.containsKey(arr[0])==true)
            {
                ht.get(arr[0]).addAdj(arr[1],Integer.parseInt(arr[2]));
            }
            if(ht.containsKey(arr[0])==false)
            {
                ht.put(arr[0],new vertex(arr[0],arr[1],Integer.parseInt(arr[2])));
            }
            if(ht.containsKey(arr[1])==false)
            {
                ht.put(arr[1],new vertex(arr[1],null,0));
            }

        }

        String start = "A";

        Dijkstra(ht, start);

        String location = "C";

        if(ht.get(location).getWeight()!=Integer.MAX_VALUE/2)
            System.out.println("Shortest Distance from "+start+" to "+location+" is "+ ht.get(location).getWeight());
        else
            System.out.println("There is no shortest path");
    }
    public static void Dijkstra(Hashtable<String,vertex> ht,String start)
    {
        ht.get(start).setWeight(0);
        Set<String> keys = ht.keySet();
        PriorityQueue<vertex> pq = new PriorityQueue<>();
        HashSet<String> hs = new HashSet<>();

        pq.offer(ht.get(start));

        while(pq.isEmpty()==false)
        {
            pq.clear();

            for(String key: keys)
                if(!hs.contains(key))
                    pq.offer(ht.get(key));

            vertex v = pq.remove();
            hs.add(v.getName());

            if(v.getAdj()!=null)
            {
                LinkedList<Adjacent> ll = v.getAdj();

                while (ll.isEmpty() == false)
                {
                    Adjacent ad = ll.remove();

                    if (ht.get(ad.getName()).getWeight() > v.getWeight() + ad.getEdgeWeight())
                    {
                        ht.get(ad.getName()).setWeight(v.getWeight() + ad.getEdgeWeight());
                        ht.get(ad.getName()).setPre(v.getName());
                    }
                }
            }
        }
    }
    public static class vertex implements Comparable
    {
        private String pre;
        private int weight;
        private LinkedList<Adjacent> adj= new LinkedList<>();
        private String name;


        public vertex(String name, String adjName, int edgeWeight)
        {
            this.name=name;
            pre="";
            weight=Integer.MAX_VALUE/2;
            if(adjName!=null)
                adj.add(new Adjacent(adjName,edgeWeight));
        }

        public String getPre()
        {
            return pre;
        }

        public void setPre(String pre)
        {
            this.pre = pre;
        }

        public int getWeight()
        {
            return weight;
        }

        public void setWeight(int weight)
        {
            this.weight = weight;
        }

        public void addAdj(String name, int edgeWeight)
        {
            adj.add(new Adjacent(name,edgeWeight));
        }

        public LinkedList<Adjacent> getAdj()
        {
            if(adj.isEmpty()==false)
                return adj;
            return null;
        }

        public String getName()
        {
            return name;
        }

        public void setName(String name)
        {
            this.name = name;
        }

        @Override
        public int compareTo(Object o)
        {
            vertex v = (vertex)o;
            if(weight>v.getWeight())
                return 1;
            return -1;
        }
    }
    public static class Adjacent
    {
        private String name;
        private int edgeWeight;

        public Adjacent(String name, int edgeWeight)
        {
            this.name=name;
            this.edgeWeight=edgeWeight;
        }

        public String getName()
        {
            return name;
        }

        public void setName(String name)
        {
            this.name = name;
        }

        public int getEdgeWeight()
        {
            return edgeWeight;
        }

        public void setEdgeWeight(int edgeWeight)
        {
            this.edgeWeight = edgeWeight;
        }
    }
}

//hashtable with name as key and value as object storing weight and everything