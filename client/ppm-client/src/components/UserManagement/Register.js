import React, { Component } from 'react';
import {createNewUser} from "../../actions/SecurityActions"
import PropTypes from 'prop-types';
import classnames from 'classnames';
import { connect } from "react-redux"
 class Register extends Component {

    constructor(){
        super();
        this.state={
            "username": "",
            "fullname": "",
            "password": "",
            "confirmPassword": "",
            "errors":{}
        };
        this.onChange = this.onChange.bind(this);
        this.onSubmit = this.onSubmit.bind(this);

    }
    componentDidMount() {
        if(this.props.security.validToken){
            this.props.history.push("/dashboard")
        }
    }

    componentWillReceiveProps(nextProps){
        if(nextProps.errors){
            this.setState({errors : nextProps.errors})
        }

    }


    onSubmit(e){
        e.preventDefault();
        const newUser = {
            "username": this.state.username,
            "fullname": this.state.fullname,
            "password": this.state.password,
            "confirmPassword": this.state.confirmPassword,
        }

        console.log({onSubmit: newUser});
        

        this.props.createNewUser(newUser, this.props.history)
    }
    onChange(e){
        this.setState({[e.target.name]:e.target.value})
    }
    render() {

        const {errors} = this.state;
        return (
            <div className="register">
            <div className="container">
                <div className="row">
                    <div className="col-md-8 m-auto">
                        <h1 className="display-4 text-center">Sign Up</h1>
                        <p className="lead text-center">Create your Account</p>
                        <form onSubmit={this.onSubmit}>
                            <div className="form-group">
                                <input type="text" 
                                className={classnames("form-control form-control-lg", {
                                    "is-invalid" : errors.fullname
                                })} placeholder="Full name" name="fullname"
                                    value={this.state.fullname}  
                                    onChange={this.onChange}
                                    />
                                    {
                                        errors.fullname &&(<div className="invalid-feedback">{errors.fullname}</div>)
                                    }
                            </div>
                            <div className="form-group">
                                <input type="text" className="form-control form-control-lg" placeholder="Email Address (User Name)" name="username" value={this.state.username}
                                onChange={this.onChange}
                                />
    
                            </div>
                            <div className="form-group">
                                <input type="password" className="form-control form-control-lg" placeholder="Password" name="password" value={this.state.password}
                                onChange={this.onChange}
                                />
                            </div>
                            <div className="form-group">
                                <input type="password" className="form-control form-control-lg" placeholder="Confirm Password"
                                    name="confirmPassword" value={this.state.confirmPassword}
                                    onChange={this.onChange}
                                    />
                            </div>
                            <input type="submit" className="btn btn-info btn-block mt-4" />
                        </form>
                    </div>
                </div>
            </div>
        </div>
        )
    }
}

Register.propTypes = {
        createNewUser : PropTypes.func.isRequired,
        errors : PropTypes.object.isRequired, 
        securuty : PropTypes.object.isRequired
}

const mapStateToProps = state =>({
    errors : state.errors, 
    security : state.security
});

export default connect (mapStateToProps, {createNewUser})(Register);