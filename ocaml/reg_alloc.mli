exception Nested_Function;;
type storage =
   | Reg of int (* register number *)
   | Stack of int (* offset in stack *)

type regAlloc = (Id.t, storage) Hashtbl.t ref
type fregAlloc = (Id.t, regAlloc) Hashtbl.t ref
type t = { ra:regAlloc ;  fra:fregAlloc option }
val getRA : t -> regAlloc
val getFRA : t -> fregAlloc

val f : Syntax.t -> t