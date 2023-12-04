import {
  NativeModules,
  StyleSheet,
  Text,
  View,
  TextInput,
  Button,
} from "react-native";
import React, { useEffect, useState } from "react";
import { BridgeServer } from "react-native-http-bridge-refurbished";
// import NetInfo from "@react-native-community/netinfo";
import { NetworkInfo } from "react-native-network-info";
// import DeviceInfo from "react-native-device-info";

// respond with "hello world" when a GET request is made to the homepage

// app.post('/', (req, res) => {
// frida.requestIntegrityToken(
//   "YklkWmNraHVOaVJWYkU5eXZrOWpqdz09",
//   (res: any) => {
//     console.log(
//       "frida.requestIntegrityToken--------------------",
//       res
//     );
//     if(res){
//       res.send('hello world')
//     }
//   }
//   );

// })
const App = () => {
  const { frida } = NativeModules;
  const [input, setInput] = useState(
    __DEV__ ? "YklkWmNraHVOaVJWYkU5eXZrOWpqdz09" : ""
  );
  const [lastCalled, setLastCalled] = useState<number | undefined>();
  const [output, setOutput] = useState("");
  const [brigewalue, setValue] = useState("");
  const [ip, setIp] = useState("");
  // const net =async ()=>{
  //    NetworkInfo.getIPAddress().then((data)=>{
  //     return data;
  //    });
  // }
  useEffect(() => {
    NetworkInfo.getIPV4Address().then((ipAddress:any) => {
      console.log(ipAddress);
      setIp(ipAddress);
    });  
    // console.log("netinfo",net());      
  }, [ip]);
  useEffect(() => {
    const server = new BridgeServer("http_service", true);
    server.post("/", async (req, res) => {
      console.log("Body----------------------------", req);
      const result: any = req.postData;
      setValue(JSON.parse(result).nounce);
      frida.requestIntegrityToken(
        JSON.parse(result).nounce,
        JSON.parse(result).cloudProjectNumber,
        (response: any) => {
          console.log(
            "frida.requestIntegrityToken--------------------",
            response
          );
          // setOutput(response);
          res.json({ message: response });
        }
      );
      // do something
      setLastCalled(Date.now());
      // or res.json({message: 'OK'});
    });
    server.listen(3000);

    return () => {
      server.stop();
    };
  }, []);
  return (
    <View style={styles.container}>
      <Text>App</Text>
      <Text>{ip}</Text>
      <TextInput
        style={{ borderWidth: 1, width: "80%", textAlign: "center" }}
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
              if (res) {
                setOutput(res);
              }
            }
          );
        }}
      />
      <Text>
        {lastCalled === undefined
          ? "Request webserver to change text"
          : "Called at " +
            new Date(lastCalled).toLocaleString() +
            " noumes is " +
            brigewalue}
      </Text>
      <Text>{output}</Text>
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
