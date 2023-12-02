import {
  NativeModules,
  StyleSheet,
  Text,
  View,
  TextInput,
  Button,
} from "react-native";
import React, { useEffect, useState } from "react";

const App = () => {
  const { frida } = NativeModules;
  const [input, setInput] = useState(__DEV__?"YklkWmNraHVOaVJWYkU5eXZrOWpqdz09":"");
  const[output,setOutput]=useState("");
  useEffect(() => {
    console.log("Frida--------------------------", frida);
  }, []);
  return (
    <View style={styles.container}>
      <Text>App</Text>
      <TextInput
      style={{borderWidth:1,width:'80%',textAlign:'center'}}
        value={input}
        onChangeText={(val) => {
          setInput(val);
        }}
      />
      <Button
        title="Get"
        onPress={() => {
          frida.requestIntegrityToken(
            "YklkWmNraHVOaVJWYkU5eXZrOWpqdz09",
            (res: any) => {
              console.log(
                "frida.requestIntegrityToken--------------------",
                res
              );
              if(res){
                setOutput(res);
              }
            }
          );
        }}
      />
      <Text>
        {output}
      </Text>
    </View>
  );
};

export default App;

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: "center",
    justifyContent: "center",
  },
});
