package generator;


public class idGenerator {
    public int[] generateId(){
        int[] allId=new int[1000];
        for(int i=0;i<allId.length;i++){
            if(i!=44 || i!=97){
                allId[i]=i+1;
            }
        }
    return allId;
    }
}
