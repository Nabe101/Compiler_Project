open Syntax

exception Nested_Function;;
type storage =
   | Reg of int (* register number *)
   | Stack of int (* offset in stack *)

type regAlloc = (Id.t, storage) Hashtbl.t ref
type fregAlloc = (Id.t, regAlloc) Hashtbl.t ref

type t = { ra:regAlloc ; fra:fregAlloc option }

let newMem in_fct = {ra = ref (Hashtbl.create 50) ; fra = if (in_fct) then None else Some(ref (Hashtbl.create 20))}
let getRA {ra=ra} = ra
let getFRA {fra=fra} = match fra with | Some(d) -> d | None -> raise Nested_Function

let add_vardec mem vardec =
   let (id,_) = vardec in 
   let d = getRA mem in
   if (not (Hashtbl.mem !d id)) then
      let n = ((Hashtbl.length !d)+1)*4 in (Hashtbl.add !d id (Stack(n)))

let rec parcours_fct mem ast =
   match ast with
   | Let (vardec, _, rest) -> (add_vardec mem vardec; parcours_fct mem rest)
   | LetTuple (vardecs, _, rest) -> (List.iter (add_vardec mem) vardecs; parcours_fct mem rest)
   | LetRec (fd, rest) -> let (name,_)=fd.name in
                        let mem2 = (newMem true) in
                       (List.iter (add_vardec mem2) fd.args;
                        parcours_fct mem2 fd.body;
                        Hashtbl.add !(getFRA mem) name (getRA mem2);
                        parcours_fct mem rest)
 | a -> ()




let f ast =
   let mem = (newMem false) in
   parcours_fct mem ast; mem;;