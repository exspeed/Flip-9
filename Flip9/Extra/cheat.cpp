#include <vector>
#include <queue>
#include <iostream>
#include <stdexcept>

using namespace std;

class node{
public:
  int position;
  vector<int> history;

  node(int p){
    position = p;
  }
};

vector<int> findBFS(queue<node> &gamestate);

int main(int argc, char* argv[]){

  if(argc != 2){
    cout << "please provide an argument" << endl;
    return 0;
  }
  
  int start = atoi(argv[1]);
  if(start >= 512 || start < 1){
    cout << "argument must be between 1-512" <<endl;
    return 0;
  }
  queue<node> gamestate;
  node first(start);
  gamestate.push(first);

  vector<int> shortest = findBFS(gamestate);

  
  cout << "Quickest solution: ";
  for(int move: shortest){
    cout << move << " ";
  }
  cout << endl;
  /*
  int j = 1;
  while(j <= 511){
    queue<node> all;
    node state(j);
    all.push(state);
    int size;
    if((size = findBFS(all).size()) > 8){
      cout << "game: " << j << "\tmoves:" << size << endl;
      }
    j++;
    }*/

  return 0;
}

int getBitmask(const int &num){
  switch(num){
  case 1: 
    return 11;
  case 2:
    return 23;
  case 3:
    return 38;
  case 4:
    return 89;
  case 5:
    return 186;
  case 6:
    return 308;
  case 7:
    return 200;
  case 8:
    return 464;
  case 9:
    return 416;
  default:
    cout << "bit mask error" << endl;
    return -1;
  }
}

vector<int> findBFS(queue<node> &gamestate){
  bool possibleState[512] = {false};
  if(gamestate.empty()){
    invalid_argument("queue is empty");
  }
  bool solution = false;
  while(!solution){
    node removedNode = gamestate.front();
    int val = removedNode.position;

    possibleState[val] = true;

    if(val == 0){
      return removedNode.history;
    }
    
    gamestate.pop();
    for(int i = 1; i <=9; i++){
      node newNode(getBitmask(i) ^ val);
      if (possibleState[newNode.position] == false){
	newNode.history = removedNode.history;
	newNode.history.push_back(i);
	gamestate.push(newNode);
	}
    }
  }
}
