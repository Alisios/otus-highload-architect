import './App.css';
import {useEffect, useState} from "react";
import {Stomp} from '@stomp/stompjs'

const App = () => {
    const [userId, setUserId] = useState(null)
    const [textAreaValue, setTextAreaValue] = useState(null)

    const socketUrl = "ws://localhost:18090/ws-endpoint/websocket";
    const socketUrl2 = "ws://localhost:18091/ws-endpoint/websocket";
    const socketUrl3 = "ws://localhost:18087/ws-endpoint/websocket";
    const subscribeUrl = '/user/queue/post'
    const sendUrl = '/app'
    const urls = [socketUrl, socketUrl2]

    const rand = Math.floor(Math.random() * urls.length);
    const client = Stomp.client(urls[rand]);
    // подключение
    const stompConnect = () => {
        // Заголовки подключения
        // const headers = {
        //     userId
        //
        // };
        const connectHeaders = {
            login: '',
            passcode: '',
            userId
        }
        console.log("Url:", rand)
        client.heartbeat.outgoing = 20000;
        client.connect(connectHeaders, connectCallback, errorCallback);
    }

    // действия при успешном подключении
    const connectCallback = function () {
        console.log('stomp connected')

        // Заголовки подписки
        const headers = {
            Authorization: `Bearer ${userId}`,
            user_id: userId
        };
        // Подписка на канал
        client.subscribe(subscribeUrl, onMessage, headers);
    };

    // Действия при ошибке подключения
    const errorCallback = function (error) {
        console.error(error.headers.message);
    };

    // Отправка сообщения
    const sendMessage = (text) => {
        client.send(sendUrl, {}, text);
    }

    // Действия при получении сообщения
    const onMessage = (message) => {
        const messageText = JSON.parse(message.body);
        console.log('message', message);
        console.log('messageText', messageText);
        setTextAreaValue(messageText)
    };

    useEffect(() => {
        // Если есть userId - подключаемся
        if (userId) stompConnect()

        // При смене Id отключаемся от сервера
        return () => {
            client.disconnect(function () {
                console.warn(`${userId} disconnect`);
            });
        }
    }, [userId])

    return (
        <div className="App">
            <header className="App-header">
                <div style={{width: '100%', marginTop: '20px'}}>
                    userId
                    <form onSubmit={(event) => {
                        event.preventDefault()
                        const value = event?.target?.elements?.userId?.value
                        setUserId(value)
                    }}>
                        <input name={'userId'} style={{padding: '10px 15px', minWidth: '400px', marginRight: '20px'}}/>
                        <button type={'submit'} style={{padding: '10px 15px', cursor: 'pointer'}}>Установить</button>
                    </form>
                </div>
                <div style={{width: '100%', marginTop: '20px'}}>
                    App User Id: {userId ? userId : 'null'}
                </div>
                {userId && <>
                    <div style={{width: '100%', marginTop: '50px'}}>
                        Входящие сообщения
                        <textarea disabled rows={20} style={{width: '100%', padding: '10px', fontSize: '14px'}}
                                  value={textAreaValue || 'Нет сообщений'}/>
                    </div>
                    <div style={{width: '100%', marginTop: '20px'}}>
                        отправить сообщение
                        <form onSubmit={(event) => {
                            event.preventDefault()
                            const text = event?.target?.elements?.message?.value
                            sendMessage(text)
                        }}>
                            <input name={'message'}
                                   style={{padding: '10px 15px', minWidth: '400px', marginRight: '20px'}}/>
                            <button type={'submit'} style={{padding: '10px 15px', cursor: 'pointer'}}>Отправить</button>
                        </form>
                    </div>
                </>
                }
            </header>
        </div>
    );
}

export default App;
