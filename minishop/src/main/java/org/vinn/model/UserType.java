package org.vinn.model;

public enum UserType {
    CUSTOMER (1, "Customer"),
    ADMIN (2, "Admin");

    private final int id;
    private final String name;

     UserType(int id, String name){
        this.id = id;
        this.name = name;
    }

    public static UserType fromId(int id){
        for(UserType type : UserType.values()){
            if(type.getId() == id)
                return type;
        }
        return null;
    }

    public int getId() {
         return this.id;
    }

    public String getName() {
        return this.name;
    }
}
