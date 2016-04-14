package com.unimelb.swen30006.metromadness.tracks;

import java.util.ArrayList;

public class LineGenerator {
    
    private ArrayList<Line> lines;
    
    public LineGenerator(ArrayList<Line> lines){
        lines = new ArrayList<Line>();
        this.lines = lines;
    }
    
    public ArrayList<Line> getLines(){
        return lines;
    }
    
    public Line getRandomLine(){
        return this.lines.get((int)Math.random()*(this.lines.size()-1));
    }
    
    
    
}