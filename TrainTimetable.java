import java.util.*;

// A program to solve 'Problem B. Train Timetable' of the 2008 Google Code Jam

// A model object to keep information about each train's arrival and departure time
class TrainTime{
  private String trainTime;
  private String startingStation;
  private int arrivalTime;
  private int departueTime;

  TrainTime(String givenTime, String givenStation){
    trainTime = givenTime;
    startingStation = givenStation;
    setArrivalTime();
    setDepartueTime();
  }
  
  public void setDepartueTime(){
    departueTime = (Integer.parseInt(trainTime.substring(0,2)) * 100) +
    (Integer.parseInt(trainTime.substring(3,5)));
  }

  public void setArrivalTime(){
    arrivalTime = (Integer.parseInt(trainTime.substring(6,8)) * 100) +
    (Integer.parseInt(trainTime.substring(9,11)));
  }

  public int getArrivalTime(){
    return arrivalTime;
  }
  public int getDepartureTime(){
    return departueTime;
  }
  public String getStartingStation(){
    return startingStation;
  }
}

class TrainTimetable {

  public static void TrainTimetable(String[] args) {
    Scanner scanner = new Scanner(System.in);

    ArrayList<TrainTime> arrTrainTime = new ArrayList<TrainTime>();

    int testCases = scanner.nextInt();
     System.out.println("testCases " + testCases);

    // From the input, indentifies the number of test cases and creates
    // Model objects for each train's journey
    for(int i = 0; i < testCases; i++ ){
      ArrayList<String> trainsFromA = new ArrayList<String>();
      ArrayList<String> trainsFromB = new ArrayList<String>();

      int bTrains = 0;
      int aTrains = 0;

      int turnAroundTime = scanner.nextInt();
      int AtoB = scanner.nextInt();
      int BtoA = scanner.nextInt();
      scanner.nextLine();

      for(int j = 0; j < AtoB; j++){
        TrainTime traintime = new TrainTime(scanner.nextLine(), "A");
        arrTrainTime.add(traintime);
      }
      for(int j = 0; j < BtoA; j++){
        TrainTime traintime = new TrainTime(scanner.nextLine(), "B");
        arrTrainTime.add(traintime);
      }


    // creates a journey for each train untill there are no train times left
    while(arrTrainTime.size() > 0){

      int lowestArrival = 2400;
      int positionLA = 0;

      // Find which train journey is the earliest
      for(int j = 0; j < arrTrainTime.size();j++){
      if(arrTrainTime.get(j).getArrivalTime() <= lowestArrival){
          lowestArrival = arrTrainTime.get(j).getArrivalTime();
          positionLA = j;
        }
      }
      // Determins whether the train starts at A or B
      if(arrTrainTime.get(positionLA).getStartingStation().equals("A")){
        aTrains++;
      }
      else{
        bTrains++;
      }
        // Tries to connect the current journey to another journey if possible
        while(true){
          int lowestDeparture = 2400;
          int positionLD = 0;
          

          // Checks if there are any journeys that start at the station we are arriving at
          // And if we arrive at the staion in time to fullfil the journey
          // We then choose the earilest journey available
    
          for(int j = 0; j < arrTrainTime.size(); j++){
            if(arrTrainTime.get(j).getDepartureTime() <= lowestDeparture 
            && arrTrainTime.get(j).getDepartureTime() >= (lowestArrival + turnAroundTime) ){

              if(!arrTrainTime.get(j).getStartingStation().equals(arrTrainTime.get(positionLA).getStartingStation()) ){
                lowestDeparture = arrTrainTime.get(j).getDepartureTime();
                positionLD = j;
              }
            }
          }
          // Sets the new arrival time
          lowestArrival = arrTrainTime.get(positionLD).getArrivalTime();

          // Remove the completed journey from the array, and adjust our positions if neccesary
          arrTrainTime.remove(positionLA);
          if(positionLD > positionLA){
            positionLD--;
          }

          positionLA = positionLD;
          
          // If there are no more journeys we can fullfill, exit.
          if(lowestDeparture == 2400){
            break;
          }
        }
      }
      System.out.println("Case #" + (i+1) + ": " + aTrains + " " + bTrains);
    }
  }
}
