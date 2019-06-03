package com.example.cinema.vo;

public class HallForm {
    private Integer id;
    private String name;
    private Integer column;
    private Integer row;

    public Integer getId(){
        return id;
    }
    public void setId(Integer id){
        this.id=id;
    }
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name=name;
    }
    public Integer getColumn(){
        return column;
    }
    public void setColumn(Integer column){
        this.column=column;
    }
    public Integer getRow(){
        return row;
    }
    public void setRow(Integer row){
        this.row=row;
    }

}
