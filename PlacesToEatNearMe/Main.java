import java.io.*;
import java.util.*;

//Restaurant class defined to store the x, y coordinates and cost of each restaurant
class Restaurant{
    int x, y; //coordinates
    double cost;

    public Restaurant(int x, int y, double cost){
        this.x=x;
        this.y=y;
        this.cost=cost;
    }
}

//Node class defined to store the x, y coordinates, cost, count, left and right children of each node in 2-d tree
class Node{
    int x, y;
    double cost;
    int count;
    Node left, right;

    public Node(int x, int y, double cost){
        this.x=x;
        this.y=y;
        this.cost=cost;
        this.count=1;
    }
}

//Tree class defined to implement the 2-d tree operations such as insert and search
class Tree{
    Node root;
    public void insert(int x, int y, double cost){
        root=insert(root, x, y, cost, 0);
    }
    private Node insert(Node node, int x, int y, double cost,int depth){
        if(node==null){
            return new Node(x, y, cost);
        }
        if(depth%2==0){
            if(x<=node.x){
                node.left=insert(node.left, x, y, cost, depth+1);
            }else{
                node.right=insert(node.right, x, y, cost, depth+1);
            }
        }else{
            if(y<=node.y){
                node.left=insert(node.left, x, y, cost, depth+1);
            }else{
                node.right=insert(node.right, x, y, cost, depth+1);
            }
        }
        node.count++;
        return node;
    }

    public int search(int x, int y, double budget){
        return search(root, x, y, budget, 0);
    }
    private int search(Node node, int x, int y, double budget, int depth){
        if(node==null){
            return 0;
        }
        int count=0;
        if(Math.abs(x-node.x)<=100 && Math.abs(y-node.y)<=100 && node.cost<=budget){
            count++;
        }

        if(depth%2==0){
            if(x<=node.x){
                count+=search(node.left, x, y, budget, depth+1);
            }
            if(x+100 > node.x){
                count+=search(node.right, x, y, budget, depth+1);
            }
        }else{
            if(y<=node.y){
                count+=search(node.left, x, y, budget, depth+1);
            }
            if(y+100>node.y){
                count+=search(node.right, x, y, budget, depth+1);
            }
        }
        return count;
    }
}

public class Main{
    public static void main(String[] args) throws IOException{
        Tree tree=new Tree();

        //read restaurants from file
        List<Restaurant> restaurants=new ArrayList<>();
        try(BufferedReader br=new BufferedReader(new FileReader("restaurants.txt"))){
            String line;
            while((line=br.readLine())!=null){
                String[] parts=line.split(",");
                int x=Integer.parseInt(parts[0]);
                int y=Integer.parseInt(parts[1]);
                double cost=Double.parseDouble(parts[2]);
                restaurants.add(new Restaurant(x, y, cost));
                tree.insert(x, y, cost);
            }
        }

        //read queries from file
        try(BufferedReader br= new BufferedReader(new FileReader("queries.txt"))){
            String line;
            while((line=br.readLine())!=null){
                String[] parts=line.split(",");
                int x=Integer.parseInt(parts[0]);
                int y=Integer.parseInt(parts[1]);
                double budget=Double.parseDouble(parts[2]);
                int count=tree.search(x,y,budget);
                System.out.println("Number of restaurants near (" + x + ", "+ y + ") within budget "+ budget + ": "+ count);
            }
        }
    }
}