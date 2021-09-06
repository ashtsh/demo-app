import {Link} from 'react-router-dom';
import { useState } from 'react';
const Home = () => {
    const [key,setKey] = useState("");
    const inputHandler = (event) => {
        setKey(event.target.value);
    };
    const clickHandler = () => {
        fetch('http://localhost:8080/add-key?key='+key);
        setKey("");
        };

    
    return (<div className="home-div">
        <h2>Available functions</h2>
        
        <ul>
            <li><Link to="/get">/Get</Link></li>
            <li><Link to="/search">/Search</Link></li>
            <li><input type="text" value={key} placeholder="Enter Key" onChange={inputHandler}></input>
            <button type="button" onClick={clickHandler}>Add Key</button></li>
        </ul>
    </div>);
}

export default Home;