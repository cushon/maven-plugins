package org.apache.maven.plugin.assembly.interpolation;

/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */


import org.apache.maven.model.Model;
import org.apache.maven.plugin.assembly.io.DefaultAssemblyReader;
import org.apache.maven.plugin.assembly.utils.InterpolationConstants;
import org.apache.maven.plugins.assembly.model.Assembly;
import org.apache.maven.plugins.assembly.model.DependencySet;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import junit.framework.TestCase;
import org.codehaus.plexus.interpolation.immutable.FixedStringSearchInterpolator;

public class AssemblyInterpolator2Test
    extends TestCase
{

    public void testDependencyOutputFileNameMappingsAreNotInterpolated() 
        throws IOException, AssemblyInterpolationException
    {
        AssemblyInterpolator interpolator = new AssemblyInterpolator();

        Model model = new Model();
        model.setArtifactId( "artifact-id" );
        model.setGroupId( "group.id" );
        model.setVersion( "1" );

        Assembly assembly = new Assembly();

        DependencySet set = new DependencySet();
        set.setOutputFileNameMapping( "${artifact.artifactId}.${artifact.extension}" );

        assembly.addDependencySet( set );

        Assembly outputAssembly = interpolator.interpolate( assembly, model, Collections.EMPTY_MAP,
                                                            DefaultAssemblyReader.create(model));
        
        List outputDependencySets = outputAssembly.getDependencySets();
        assertEquals( 1, outputDependencySets.size() );
        
        DependencySet outputSet = (DependencySet) outputDependencySets.get( 0 );
        
        assertEquals( set.getOutputFileNameMapping(), outputSet.getOutputFileNameMapping() );
    }

}
