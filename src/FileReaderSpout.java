
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.IRichSpout;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;

public class FileReaderSpout implements IRichSpout {
  private SpoutOutputCollector _collector;
  private TopologyContext context;
  private FileReader fileReader;
  private boolean completed = false;

  @Override
  public void open(Map conf, TopologyContext context,
                   SpoutOutputCollector collector) {

	
	
	
     /*
    ----------------------TODO-----------------------
    Task: initialize the file reader


    ------------------------------------------------- */
	try{
    	    this.context = context;
   	 //   this._collector = collector;
 	    this.fileReader = new FileReader(conf.get("inputFile").toString());
	} catch (FileNotFoundException e) {
	    throw new RuntimeException("Error reading file " + conf.get("inputFile"));
	}
	this._collector = collector;
 }

  @Override
  public void nextTuple() {

	if (completed) {
	    try  {
		Thread.sleep(1000);
	    } catch(InterruptedException e) {}
	}
     	String str;
	BufferedReader reader = new BufferedReader(fileReader);
	try {
	    while((str = reader.readLine())!=null){
	        this._collector.emit(new Values(str), str);
	    }
	}  catch(Exception e) {
	    throw new RuntimeException("Error reading tuple", e);
	} finally {
	    completed = true;
	}
	/*
    ----------------------TODO-----------------------
    Task:
    1. read the next line and emit a tuple for it
    2. don't forget to sleep when the file is entirely read to prevent a busy-loop

    ------------------------------------------------- */


  }

  @Override
  public void declareOutputFields(OutputFieldsDeclarer declarer) {

    declarer.declare(new Fields("word"));

  }

  @Override
  public void close() {
     try{
	 fileReader.close();
     } catch(IOException e) {
	 e.printStackTrace(); 
     }
   /*
    ----------------------TODO-----------------------
    Task: close the file


    ------------------------------------------------- */

  }


  @Override
  public void activate() {
  }

  @Override
  public void deactivate() {
  }

  @Override
  public void ack(Object msgId) {
  }

  @Override
  public void fail(Object msgId) {
  }

  @Override
  public Map<String, Object> getComponentConfiguration() {
    return null;
  }
}
