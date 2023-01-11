let arm nom =
  let file = open_out (nom ^ ".arm") in
  let text = "hello" in 
  Printf.fprintf file "%s \n" text;
  (* let rec aux abr = 
  | FAdd($1, $3) -> Printf.fprintf file "%s\n" message; *)


  close_out file;;