import logo from './logo.svg';
import { Route,Switch } from 'react-router-dom';
import './App.css';
import GetResults from './getResults';

import SearchPage from './searchPage';
import Home from './home';

function App(props) {
  return (
    <div className="App">
      <Switch>
      <Route path="/" exact>
        <Home />
     </Route>
     <Route path="/get" exact>
        <GetResults/>
     </Route>
     <Route path="/search" exact>
        <SearchPage/>
     </Route>
     </Switch>
    </div>
  );
}

export default App;
