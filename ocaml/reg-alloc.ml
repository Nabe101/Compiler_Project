type storage =
   | Reg of int (* register number *)
   | Stack of int (* offset in stack *)




let rec f_rec d ast =
   match ast with
 | Not a -> f_rec d a
 | Neg a -> f_rec d a
 | Add (a1, a2) -> (f_rec d a1; f_rec d a2)
 | Sub (a1, a2) -> (f_rec d a1; f_rec d a2)
 | FNeg a -> f_rec d a
 | FAdd (a1, a2) -> (f_rec d a1; f_rec d a2)
 | FSub (a1, a2) -> (f_rec d a1; f_rec d a2)
 | FMul (a1, a2) -> (f_rec d a1; f_rec d a2)
 | FDiv (a1, a2) -> (f_rec d a1; f_rec d a2)
 | Eq (a1, a2) -> (f_rec d a1; f_rec d a2)
 | LE (a1, a2) -> (f_rec d a1; f_rec d a2)
 | If (a1, a2, a3) -> (f_rec d a1; f_rec d a2; f_rec d a3)
 | App (a1, la2) -> (f_rec d a1; List.iter (f_rec d) a2)
 | Get(a1, a2) -> (f_rec d a1; f_rec d a2)
 | Put(a1, a2, a3) -> (f_rec d a1; f_rec d a2; f_rec d a3)
 | Tuple(l) -> List.iter (f_rec d) a2
 | Array(a1,a2) -> (f_rec d a1; f_rec d a2)
 | LetTuple (l, a1, a2) -> (List.iter (f_rec d) a2;f_rec d a1; f_rec d a2)


 | Let ((id,t), a1, a2) -> if (not (Hashtbl.mem !d id)) then
                              let n = ((Hashtbl.length !d)+1)*4 in (Hashtbl.add !d id Stack(n))
 | LetRec (fd, a) -> if (not (Hashtbl.mem !d id)) then
                       let n = ((Hashtbl.length !d)+1)*4 in (Hashtbl.add !d id Stack(n))
 | Var id -> if (not (Hashtbl.mem !d id)) then
               let n = ((Hashtbl.length !d)+1)*4 in (Hashtbl.add !d id Stack(n))



let f ast =
   let d = ref (Hashtbl.create 100) in
   f_rec d ast;;