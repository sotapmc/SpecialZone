package org.sotap.SpecialZone;

public class zone {
    private SpecialZone plug;
    String worldname,zonename;
    double x1,x2,y1,y2,z1,z2;
    boolean ignore_Y,keepInv,keepExp ;
    public zone(String[] args,SpecialZone plug){
        this.zonename=args[0];
        this.x1=Integer.valueOf(args[1]);
        this.x2=Integer.valueOf(args[2]);
        this.y1=Integer.valueOf(args[3]);
        this.y2=Integer.valueOf(args[4]);
        this.z1=Integer.valueOf(args[5]);
        this.z2=Integer.valueOf(args[6]);
        this.worldname=args[7];
        //ignore_Y is true when you want to check if a player is in this specialzone without checking his Y coordinate.
        this.ignore_Y=false;
        if(args[8].equalsIgnoreCase("true")){
            this.ignore_Y=true;
            this.plug = plug;
        }


        this.keepInv = false;
        this.keepExp = false;
    }

    public void setASpecialZone(){
        plug.getConfig().set("SpecialZone." + zonename + ".x1", x1);
        plug.getConfig().set("SpecialZone." + zonename + ".x2", x2);
        plug.getConfig().set("SpecialZone." + zonename + ".y1", y1);
        plug.getConfig().set("SpecialZone." + zonename + ".y2", y2);
		plug.getConfig().set("SpecialZone." + zonename + ".z1", z1);
		plug.getConfig().set("SpecialZone." + zonename + ".z2", z2);
        plug.getConfig().set("SpecialZone." + zonename + ".world_name", worldname);
        plug.getConfig().set("SpecialZone." + zonename + ".ignore_Y", ignore_Y);
		// don't forget to save the configuration
		plug.saveConfig();
    }

    public boolean isInZone(double x,double y,double z,String worldname){
        if (this.ignore_Y){
            return (this.x1-x)*(this.x2-x)<0 && (this.z1-z)*(this.z2-z)<0 && this.worldname.equalsIgnoreCase(worldname);
        }
        return (this.x1-x)*(this.x2-x)<0 && (this.y1-y)*(this.y2-y)<0 && (this.z1-z)*(this.z2-z)<0 && this.worldname.equalsIgnoreCase(worldname);
    }


    public void setKeepInv(boolean flag){
        this.keepInv = flag;
    }

    public boolean getKeepInv(){
        return this.keepInv;
    }

    public void setKeepExp(boolean flag){
        this.keepInv = flag;
    }

    public boolean getKeepExp(){
        return this.keepExp;
    }
}