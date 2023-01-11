(* Returns the index of the first occurence of x in l, or -1 if x is not in l
   cpr is the comparator : the function that compares two elements of l and returns True if they're equal *)
let rec index (x:'a) (l:'a list) (cpr:'a -> 'a -> bool) =
  match l with
  | [] -> -1
  | elt :: tail -> if (cpr x elt) then 0 else
                     let id = (index x tail cpr) in
                     if (id==(-1)) then (-1) else (id+1);;

(* Returns the assembly code to load the value in the stack at offset o into register r *)
let load o r =;;

(* Returns the assembly code to store the value in register r into the stack at offset o *)
let store r o =;;



let rec f code =